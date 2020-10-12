package br.com.joao.ourdigitalbank.service.client.impl;


import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.repository.client.ClientRepository;
import br.com.joao.ourdigitalbank.service.client.ClientService;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;


@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Client saveClient(Client client) throws Exception {

        Client clientByEmail = clientRepository.findByEmail(client.getEmail());
        Client clientByCPF = clientRepository.findByCpf(client.getCpf());

        if (clientByCPF == null && clientByEmail == null){
            return clientRepository.save(client);
        } else{
            return null;
        }
    }

    @Override
    public Client findClient(Long id) {
        try{
            return clientRepository.findById(id).orElseThrow(()->
                    new NotFoundException("Client: " + id + "not found"));
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Client findClientByEmailAndCpf(String cpf, String email) {
        return clientRepository.findByCpfAndEmail(cpf, email);
    }
}
