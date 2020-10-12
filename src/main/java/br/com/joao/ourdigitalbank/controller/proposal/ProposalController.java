package br.com.joao.ourdigitalbank.controller.proposal;

import br.com.joao.ourdigitalbank.controller.proposal.dto.ProposalRequest;
import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.exception.ProposalNotFoundException;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.enumeration.StatusProposalEnum;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import br.com.joao.ourdigitalbank.service.client.ClientService;
import br.com.joao.ourdigitalbank.service.proposal.ProposalService;
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
@RequestMapping("ourbank/proposal")
public class ProposalController {

    private final ProposalService proposalService;
    private final ClientService clientService;


    public ProposalController(ProposalService proposalService, ClientService clientService) {
        this.proposalService = proposalService;
        this.clientService = clientService;
    }


    @GetMapping("client/{id}")
    public ResponseEntity<Response<ProposalRequest>> findProposal(@PathVariable("id") Long clientId) throws ProposalNotFoundException {

        Response<ProposalRequest> response = new Response<>();

        Client client = clientService.findClient(clientId);

        if(client == null || client.getAddress() == null || client.getFileCNH() ==null){
            response.addErrorMsgToResponse("The previous steps are incomplete");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }

        Proposal proposal = proposalService.findByClient(client);

        if(proposal == null){
            response.addErrorMsgToResponse("Proposal could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ProposalRequest dto = proposal.convertEntityToDTO();

        createSelfLink(proposal, dto);
        response.setData(dto);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/proposal/submit");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PutMapping("submit/{id}")
    public ResponseEntity<Response<ProposalRequest>> saveProposal(@Valid @RequestBody ProposalRequest proposalRequest, @Valid BindingResult result, @PathVariable("id") Long id) throws ProposalNotFoundException {

        Response<ProposalRequest> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }


        Proposal proposal = proposalService.findById(id);

        if(proposal == null){
            response.addErrorMsgToResponse("Proposal could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        proposal.setAccept(proposalRequest.getAccept());

        Proposal proposalToCreate = proposalService.saveProposal(proposal);

        if(proposalToCreate.getAccept().equals(StatusProposalEnum.REFUSED)){
            EmailConfig emailConfig = new EmailConfig();
            emailConfig.sendEmailFake(emailConfig.generateContent(proposal),  "OurDigitalBank",proposal.getClient().getEmail());
        }


        ProposalRequest dtoSaved = proposalToCreate.convertEntityToDTO();

        createSelfLink(proposal, dtoSaved);
        response.setData(dtoSaved);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/account/");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }

    private void createSelfLink(Proposal proposal, ProposalRequest proposalRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(ProposalController.class).slash(proposal.getId()).withSelfRel();
        proposalRequest.add(selfLink);
    }
}
