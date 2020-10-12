package br.com.joao.ourdigitalbank.controller.proposal.dto;

import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.enumeration.StatusProposalEnum;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class ProposalRequest extends RepresentationModel<ProposalRequest> {

    private Long id;

    private StatusProposalEnum accept;

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

    public Proposal convertDTOToEntity() {
        return new ModelMapper().map(this, Proposal.class);
    }
}
