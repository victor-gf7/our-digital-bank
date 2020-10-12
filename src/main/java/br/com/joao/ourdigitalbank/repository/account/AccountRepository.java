package br.com.joao.ourdigitalbank.repository.account;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByProposal(Proposal proposal);

    Account findByAgencyNumberAndAccountNumber(int agencyNumber, int accountNumber);

    Account findByAccountNumber(int accountNumber);

}
