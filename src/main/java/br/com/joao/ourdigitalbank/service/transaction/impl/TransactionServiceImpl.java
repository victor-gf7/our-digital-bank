package br.com.joao.ourdigitalbank.service.transaction.impl;

import br.com.joao.ourdigitalbank.model.transaction.Transaction;
import br.com.joao.ourdigitalbank.repository.transaction.TransactionRepository;
import br.com.joao.ourdigitalbank.service.transaction.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction processTransfer(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findByNsu(int nsu) {

        return transactionRepository.findByNsu(nsu);
    }
}
