package br.com.joao.ourdigitalbank.controller.token.dto;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.token.Token;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenRequest extends RepresentationModel<TokenRequest> {

    private int token;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime expireDate;

    private boolean used;

    private int timeExpireDateDays;

    private Account account;


    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getTimeExpireDateDays() {
        return timeExpireDateDays;
    }

    public void setTimeExpireDateDays(int timeExpireDateDays) {
        this.timeExpireDateDays = timeExpireDateDays;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Token convertDTOToEntity() {
        return new ModelMapper().map(this, Token.class);
    }
}
