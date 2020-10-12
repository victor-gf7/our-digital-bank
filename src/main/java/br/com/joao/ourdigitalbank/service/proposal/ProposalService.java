package br.com.joao.ourdigitalbank.service.proposal;

import br.com.joao.ourdigitalbank.exception.ProposalNotFoundException;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;


public interface ProposalService {


    Proposal saveProposal(Proposal proposal);

    Proposal findById(Long id) throws ProposalNotFoundException;

    Proposal findByClient(Client client);
}
