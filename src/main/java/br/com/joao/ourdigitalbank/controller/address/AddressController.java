package br.com.joao.ourdigitalbank.controller.address;


import br.com.joao.ourdigitalbank.controller.address.dto.AddressRequest;
import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.model.address.Address;
import br.com.joao.ourdigitalbank.service.address.AddressService;
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
@RequestMapping("ourbank/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/")
    public ResponseEntity<Response<AddressRequest>> saveAddress(@RequestHeader Long clientId, @RequestBody AddressRequest addressRequest, @Valid BindingResult result) throws Exception {

        Response<AddressRequest> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Address address = addressRequest.convertDTOToEntity();
        Address addressToCreate = addressService.saveAddress(address, clientId);

        if(addressToCreate == null){
            response.addErrorMsgToResponse("The first part of the registration is not complete or incorrect. Please complete the registration correctly.");
            return ResponseEntity.badRequest().body(response);
        }

        AddressRequest dtoSaved = addressToCreate.convertEntityToDTO();
        createSelfLink(addressToCreate,dtoSaved);

        response.setData(dtoSaved);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/upload/");

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    private void createSelfLink(Address address, AddressRequest addressRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(AddressController.class).slash(address.getId()).withSelfRel();
        addressRequest.add(selfLink);
    }

}
