package br.com.joao.ourdigitalbank.service.transaction;

import br.com.joao.ourdigitalbank.model.transaction.Transaction;

public interface TransactionService {

    Transaction processTransfer(Transaction transaction);

    Transaction findByNsu(int nsu);
}
