package br.com.joao.ourdigitalbank.service.token.impl;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.token.Token;
import br.com.joao.ourdigitalbank.repository.token.TokenRepository;
import br.com.joao.ourdigitalbank.service.token.TokenService;
import br.com.joao.ourdigitalbank.utils.EmailConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token generateToken(Token token) {

        Random random = new Random();
        token.setToken(random.nextInt((999999 - 100000) + 1) + 100000);
        token.setExpireDate(LocalDateTime.now().plusDays(token.getTimeExpireDateDays()));

        return tokenRepository.save(token);
    }

    @Override
    public Token findToken(Account account) {
        return tokenRepository.findByAccount(account);
    }

    @Override
    public Token updateToken(Token token) {
        return tokenRepository.save(token);
    }
}
