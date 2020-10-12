package br.com.joao.ourdigitalbank.repository.transaction;

import br.com.joao.ourdigitalbank.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByNsu(int nsu);
}
