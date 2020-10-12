package br.com.joao.ourdigitalbank.controller.address.dto;


import br.com.joao.ourdigitalbank.model.address.Address;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

public class AddressRequest extends RepresentationModel<AddressRequest> {

    @NotBlank(message = "zipcode is a required attribute")
    private String zipCode;

    @NotBlank(message = "street is a required attribute")
    private String street;

    @NotBlank(message = "neighborhood is a required attribute")
    private String neighborhood;

    @NotBlank(message = "complement is a required attribute")
    private String complement;

    @NotBlank(message = "city is a required attribute")
    private String city;

    @NotBlank(message = "state is a required attribute")
    private String state;


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Address convertDTOToEntity() {
        return new ModelMapper().map(this, Address.class);
    }

}
