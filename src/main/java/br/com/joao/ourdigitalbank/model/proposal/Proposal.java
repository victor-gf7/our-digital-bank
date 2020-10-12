package br.com.joao.ourdigitalbank.model.proposal;


import br.com.joao.ourdigitalbank.controller.proposal.dto.ProposalRequest;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.enumeration.StatusProposalEnum;
import net.minidev.json.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProposalEnum accept;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusProposalEnum getAccept() {
        return accept;
    }

    public void setAccept(StatusProposalEnum accept) {
        this.accept = accept;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ProposalRequest convertEntityToDTO() {
        return new ModelMapper().map(this, ProposalRequest.class);
    }
}
