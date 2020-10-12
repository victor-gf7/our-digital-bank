package br.com.joao.ourdigitalbank.model.token;

import br.com.joao.ourdigitalbank.controller.token.dto.TokenRequest;
import br.com.joao.ourdigitalbank.model.account.Account;
import net.minidev.json.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int token;

    private LocalDateTime expireDate;

    private boolean used;

    private int timeExpireDateDays;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public TokenRequest convertEntityToDTO() {
        return new ModelMapper().map(this, TokenRequest.class);
    }
}
