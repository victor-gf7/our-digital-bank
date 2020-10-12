package br.com.joao.ourdigitalbank.repository.client;

import br.com.joao.ourdigitalbank.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);
    Client findByCpf(String cpf);
    Client findByCpfAndEmail(String cpf, String email);
}
