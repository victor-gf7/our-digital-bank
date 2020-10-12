package br.com.joao.ourdigitalbank.controller.transaction;


import br.com.joao.ourdigitalbank.controller.client.ClientController;
import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.controller.transaction.dto.TransactionRequest;
import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.transaction.Transaction;
import br.com.joao.ourdigitalbank.service.account.AccountService;
import br.com.joao.ourdigitalbank.service.transaction.TransactionService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("ourbank/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostMapping("/")
    public synchronized ResponseEntity<Response<TransactionRequest>> processTransfer(@RequestBody TransactionRequest transactionRequest, @Valid BindingResult result){

        Response<TransactionRequest> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            response.addErrorMsgToResponse("There was a problem validating the transaction");
            return ResponseEntity.badRequest().body(response);
        }

        Transaction transactionToValidate = transactionService.findByNsu(transactionRequest.getNsu());

        if(transactionToValidate != null){
            TransactionRequest dtoExistent = transactionToValidate.convertEntityToDTO();

            System.out.println("Single code transfer " + transactionToValidate.getNsu() +" to bank "+ transactionToValidate.getSourceBank() +" has already been processed");

            createSelfLink(transactionToValidate, dtoExistent);
            response.setData(dtoExistent);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Header Location", "/ourbank/transaction");

            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        }

        Transaction transaction = transactionRequest.convertDTOToEntity();
        Transaction transactionToCreate = transactionService.processTransfer(transaction);

        Account account = accountService.findByAgencyNumberAndAccountNumber(transactionToCreate.getDestinationAgency(), transactionToCreate.getDestinationAccount());
        account.setBalance(transactionToCreate.getTransferValue());

        accountService.updateAccount(account);

        TransactionRequest dtoSaved = transactionToCreate.convertEntityToDTO();

        createSelfLink(transactionToCreate, dtoSaved);
        response.setData(dtoSaved);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/transaction");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private void createSelfLink(Transaction transaction, TransactionRequest transactionRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(ClientController.class).slash(transaction.getId()).withSelfRel();
        transactionRequest.add(selfLink);
    }
}
