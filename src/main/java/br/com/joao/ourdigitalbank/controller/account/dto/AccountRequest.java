package br.com.joao.ourdigitalbank.controller.account.dto;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest extends RepresentationModel<AccountRequest> {

    @NotBlank(message = "agencyNumber is a required attribute")
    private int agencyNumber;

    @NotBlank(message = "accountNumber is a required attribute")
    private int accountNumber;

    @NotBlank(message = "BankCode is a required attribute")
    private int bankCode;

    @NotBlank(message = "balance is a required attribute")
    private double balance;

    private String password;

    @NotNull(message = "proposal  is a required attribute")
    private Proposal proposal;

    public int getAgencyNumber() {
        return agencyNumber;
    }

    public void setAgencyNumber(int agencyNumber) {
        this.agencyNumber = agencyNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account convertDTOToEntity() {
        return new ModelMapper().map(this, Account.class);
    }
}
