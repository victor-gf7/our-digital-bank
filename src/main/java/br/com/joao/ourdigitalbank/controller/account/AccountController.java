package br.com.joao.ourdigitalbank.controller.account;


import br.com.joao.ourdigitalbank.controller.account.dto.AccountRequest;
import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.exception.ProposalNotFoundException;
import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.enumeration.StatusProposalEnum;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import br.com.joao.ourdigitalbank.model.token.Token;
import br.com.joao.ourdigitalbank.service.account.AccountService;
import br.com.joao.ourdigitalbank.service.proposal.ProposalService;
import br.com.joao.ourdigitalbank.service.token.TokenService;
import br.com.joao.ourdigitalbank.utils.EmailConfig;
import br.com.joao.ourdigitalbank.utils.Validator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("ourbank/account")
public class AccountController {

    private final AccountService accountService;
    private final ProposalService proposalService;
    private  final TokenService tokenService;

    public AccountController(AccountService accountService, ProposalService proposalService, TokenService tokenService) {
        this.accountService = accountService;
        this.proposalService = proposalService;
        this.tokenService = tokenService;
    }

    @PostMapping("/")
    public ResponseEntity<Response<AccountRequest>> associateAccount(@RequestBody AccountRequest accountRequest, @RequestHeader Long proposalId, @Valid BindingResult result) throws ProposalNotFoundException {

        Response<AccountRequest> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }


        Proposal proposal = proposalService.findById(proposalId);
        Account accountToCreate;

        if(proposal.getAccept().equals(StatusProposalEnum.ACCEPTED) || proposal.getAccept().equals(StatusProposalEnum.SECONDATTEMPT)){
            proposal.setAccept(StatusProposalEnum.LIBERATED);
            Account account = accountRequest.convertDTOToEntity();
            account.setProposal(proposal);
            accountToCreate = accountService.associateAccount(account);
            accountService.sendEmail(accountToCreate);
        } else if(proposal.getAccept().equals(StatusProposalEnum.WAITING)){
            proposal.setAccept(StatusProposalEnum.SECONDATTEMPT);
            proposalService.saveProposal(proposal);
            response.addErrorMsgToResponse("Proposal not yet accepted");
            return ResponseEntity.status(503).body(response);
        } else{
            response.addErrorMsgToResponse("Account could not be created");
            return ResponseEntity.unprocessableEntity().body(response);
        }

        AccountRequest dtoSaved = accountToCreate.convertEntityToDTO();


        createSelfLink(accountToCreate, dtoSaved);
        response.setData(dtoSaved);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/account/");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @GetMapping("/{account}")
    public ResponseEntity<Response<AccountRequest>> findAccount(@PathVariable("account") int accountNumber){
        Response<AccountRequest> response = new Response<>();

        Account account = accountService.findByAccountNumber(accountNumber);

        if(account == null){
            response.addErrorMsgToResponse("No account number");
            return ResponseEntity.badRequest().body(response);
        }

        AccountRequest dtoSaved = account.convertEntityToDTO();

        createSelfLink(account, dtoSaved);
        response.setData(dtoSaved);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/account/");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<AccountRequest>> createPassword(@PathVariable ("id") Long id, @RequestBody AccountRequest accountRequest, @RequestHeader Long tokenNumber, BindingResult result) throws Exception {

        Validator validator = new Validator();
        Response<AccountRequest> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        List<String> messages = validator.checkPasswordRules(accountRequest.getPassword());
        if(messages != null){
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(messages.get(0)));
            response.addErrorMsgToResponse(messages.get(0));
            return ResponseEntity.badRequest().body(response);
        }

        Account account = accountService.findById(id);

        Token token = tokenService.findToken(account);

        if(token.getToken() != tokenNumber){
            response.addErrorMsgToResponse("This is not a valid token");
            return ResponseEntity.badRequest().body(response);
        }

        if(token.isUsed()){
            response.addErrorMsgToResponse("Token unavailable because it has already been used.");
            return ResponseEntity.badRequest().body(response);
        } else if(token.getExpireDate().isBefore(LocalDateTime.now())){
            response.addErrorMsgToResponse("Expired token");
            return ResponseEntity.badRequest().body(response);
        }
        account.setPassword(accountRequest.getPassword());

        Account accountToSave = accountService.savePassword(account);

        if(accountToSave != null){
            EmailConfig emailConfig = new EmailConfig();
            emailConfig.sendEmailFake("Your password has been changed.",
                    "Password changed",
                    token.getAccount().getProposal().getClient().getEmail());
        }


        token.setAccount(accountToSave);
        token.setUsed(true);
        Token tokenToSave = tokenService.updateToken(token);

        AccountRequest dtoSaved = accountToSave.convertEntityToDTO();


        createSelfLink(accountToSave, dtoSaved);
        response.setData(dtoSaved);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/account/");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }

    private void createSelfLink(Account account, AccountRequest accountRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(AccountController.class).slash(account.getId()).withSelfRel();
        accountRequest.add(selfLink);
    }
}
