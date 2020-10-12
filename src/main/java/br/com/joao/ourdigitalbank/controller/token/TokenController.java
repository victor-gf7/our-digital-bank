package br.com.joao.ourdigitalbank.controller.token;

import br.com.joao.ourdigitalbank.controller.client.ClientController;
import br.com.joao.ourdigitalbank.controller.client.dto.ClientRequest;
import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.controller.token.dto.TokenRequest;
import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import br.com.joao.ourdigitalbank.model.token.Token;
import br.com.joao.ourdigitalbank.service.account.AccountService;
import br.com.joao.ourdigitalbank.service.client.ClientService;
import br.com.joao.ourdigitalbank.service.proposal.ProposalService;
import br.com.joao.ourdigitalbank.service.token.TokenService;
import br.com.joao.ourdigitalbank.utils.EmailConfig;
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
@RequestMapping("ourbank/token")
public class TokenController {

    private final TokenService tokenService;
    private final ClientService clientService;
    private final ProposalService proposalService;
    private final AccountService accountService;

    public TokenController(TokenService tokenService, ClientService clientService, ProposalService proposalService, AccountService accountService) {
        this.tokenService = tokenService;
        this.clientService = clientService;
        this.proposalService = proposalService;
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<Response<TokenRequest>> generateToken(@RequestBody ClientRequest clientRequest, @RequestHeader int validateDays, @Valid BindingResult result){
        Response<TokenRequest> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Client client = clientService.findClientByEmailAndCpf(clientRequest.getCpf(), clientRequest.getEmail());
        Proposal proposal = proposalService.findByClient(client);
        Account account = accountService.findByProposal(proposal);

        Token token = new Token();
        token.setTimeExpireDateDays(validateDays);
        token.setUsed(false);
        token.setAccount(account);

        Token tokenToCreate = tokenService.generateToken(token);

        if(tokenToCreate != null){
            EmailConfig emailConfig = new EmailConfig();
            emailConfig.sendEmailFake("your first access token is: " + token.getToken(),
                    "Token",
                    token.getAccount().getProposal().getClient().getEmail());
        }

        TokenRequest dtoSaved = tokenToCreate.convertEntityToDTO();

        createSelfLink(token, dtoSaved);
        response.setData(dtoSaved);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/account/");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private void createSelfLink(Token token, TokenRequest tokenRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(ClientController.class).slash(token.getId()).withSelfRel();
        tokenRequest.add(selfLink);
    }
}
