package br.com.joao.ourdigitalbank.service.account.impl;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.enumeration.StatusProposalEnum;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import br.com.joao.ourdigitalbank.repository.account.AccountRepository;
import br.com.joao.ourdigitalbank.service.account.AccountService;
import br.com.joao.ourdigitalbank.utils.EmailConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account associateAccount(Account account) {
        Random random = new Random();

        account.setAgencyNumber(random.nextInt((9999 - 1000) + 1) + 1000);
        account.setAccountNumber(random.nextInt((99999999 - 10000000) + 1) + 10000000);

        return accountRepository.save(account);
    }

    @Override
    public Account findByProposal(Proposal proposal) {
        return accountRepository.findByProposal(proposal);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(()->
                new NotFoundException("Account:" + id +"not found"));
    }

    @Override
    public Account savePassword(Account account) {
        account.setPassword(encoder().encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account findByAgencyNumberAndAccountNumber(int agencyNumber, int accountNumber) {
        return accountRepository.findByAgencyNumberAndAccountNumber(agencyNumber, accountNumber);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByAccountNumber(int accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public void sendEmail(Account account) {

        EmailConfig emailConfig = new EmailConfig();

        emailConfig.sendEmailFake(emailConfig.generateContent(account), "Welcome OurDigitalBank",account.getProposal().getClient().getEmail());

    }

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
