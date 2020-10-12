package br.com.joao.ourdigitalbank.service.token;

import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.token.Token;

public interface TokenService {

    Token generateToken(Token token);

    Token findToken(Account account);

    Token updateToken(Token token);
}
