package br.com.joao.ourdigitalbank.service.account;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;

public interface AccountService {

    Account associateAccount(Account account);

    Account findByProposal(Proposal proposal);

    Account findById(Long id);

    Account savePassword(Account account);

    Account findByAgencyNumberAndAccountNumber(int agencyNumber, int accountNumber);

    Account updateAccount(Account account);

    Account findByAccountNumber(int accountNumber);

    void sendEmail(Account account);
}
