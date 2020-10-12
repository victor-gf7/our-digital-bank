package br.com.joao.ourdigitalbank.model.client;


import br.com.joao.ourdigitalbank.controller.client.dto.ClientRequest;
import br.com.joao.ourdigitalbank.model.filecnh.FileCNH;
import br.com.joao.ourdigitalbank.model.address.Address;
import net.minidev.json.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String cpf;

    @NotNull
    private LocalDate dateBirth;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_cnh_id", referencedColumnName = "id")
    private FileCNH fileCNH;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public ClientRequest convertEntityToDTO() {
        return new ModelMapper().map(this, ClientRequest.class);
    }
}
