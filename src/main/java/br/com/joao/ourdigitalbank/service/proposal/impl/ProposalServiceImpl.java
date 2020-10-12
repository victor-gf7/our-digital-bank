package br.com.joao.ourdigitalbank.service.proposal.impl;

import br.com.joao.ourdigitalbank.exception.ProposalNotFoundException;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import br.com.joao.ourdigitalbank.repository.proposal.ProposalRepository;
import br.com.joao.ourdigitalbank.service.proposal.ProposalService;
import org.springframework.stereotype.Service;

@Service
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;

    public ProposalServiceImpl(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }


    @Override
    public Proposal saveProposal(Proposal proposal) {

        return proposalRepository.save(proposal);
    }

    @Override
    public Proposal findById(Long id) throws ProposalNotFoundException {
        return proposalRepository.findById(id).orElseThrow(()->
                new ProposalNotFoundException("Proposal id=" + id + " not found"));
    }

    @Override
    public Proposal findByClient(Client client) {
        return proposalRepository.findByClient(client);
    }

}
