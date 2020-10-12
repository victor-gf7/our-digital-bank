package br.com.joao.ourdigitalbank.controller.client;

import br.com.joao.ourdigitalbank.controller.client.dto.ClientRequest;
import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.exception.ProposalNotFoundException;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.service.client.ClientService;
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

@RestController
@RequestMapping("ourbank/client")
public class ClientController {



    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/")
    public ResponseEntity<Response<ClientRequest>> saveClient(@Valid @RequestBody ClientRequest clientRequest, @Valid BindingResult result) throws Exception {


        Response<ClientRequest> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Client client = clientRequest.convertDTOToEntity();

        if(!Validator.validateFormClient(client)){
            response.addErrorMsgToResponse("form contains invalid information");
            return ResponseEntity.badRequest().body(response);
        }
        Client clientToCreate = clientService.saveClient(client);

        if(clientToCreate == null){
            return ResponseEntity.badRequest().body(response);
        }
        ClientRequest dtoSaved = clientToCreate.convertEntityToDTO();
        createSelfLink(clientToCreate,dtoSaved);

        response.setData(dtoSaved);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/address/");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ClientRequest>> findClient(@PathVariable("id") Long clientId) throws ProposalNotFoundException {

        Response<ClientRequest> response = new Response<>();

        Client client = clientService.findClient(clientId);

        if(client == null){
            return ResponseEntity.badRequest().body(response);
        }

        ClientRequest dto = client.convertEntityToDTO();

        createSelfLink(client, dto);
        response.setData(dto);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/proposal/submit");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private void createSelfLink(Client client, ClientRequest clientRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(ClientController.class).slash(client.getId()).withSelfRel();
        clientRequest.add(selfLink);
    }

}
