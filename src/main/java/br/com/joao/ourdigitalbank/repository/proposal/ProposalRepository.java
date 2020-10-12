package br.com.joao.ourdigitalbank.repository.proposal;

import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal,Long> {

    Proposal findByClient(Client client);
}
