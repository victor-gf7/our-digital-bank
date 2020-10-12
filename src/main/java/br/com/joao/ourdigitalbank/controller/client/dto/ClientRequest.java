package br.com.joao.ourdigitalbank.controller.client.dto;


import br.com.joao.ourdigitalbank.model.address.Address;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.filecnh.FileCNH;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientRequest extends RepresentationModel<ClientRequest> {

    @NotBlank(message = "firstName is a required attribute")
    @NotNull(message = "firstName is a required attribute")
    private String firstName;

    @NotBlank(message = "lastName is a required attribute")
    @NotNull(message = "lastName is a required attribute")
    private String lastName;

    @NotBlank(message = "email is a required attribute")
    @NotNull(message = "email is a required attribute")
    private String email;

    @NotBlank(message = "cpf is a required attribute")
    @NotNull(message = "cpf is a required attribute")
    private String cpf;

    @NotNull(message = "dateBirth is a required attribute")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateBirth;

    private Address address;

    private FileCNH fileCNH;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public FileCNH getFileCNH() {
        return fileCNH;
    }

    public void setFileCNH(FileCNH fileCNH) {
        this.fileCNH = fileCNH;
    }


    public Client convertDTOToEntity() {
        return new ModelMapper().map(this, Client.class);
    }
}
