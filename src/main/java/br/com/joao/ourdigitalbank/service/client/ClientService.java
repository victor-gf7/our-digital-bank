package br.com.joao.ourdigitalbank.service.client;


import br.com.joao.ourdigitalbank.model.client.Client;

public interface ClientService {
    Client saveClient(Client client) throws Exception;

    Client findClient(Long id);

    Client findClientByEmailAndCpf(String cpf, String email);
}
