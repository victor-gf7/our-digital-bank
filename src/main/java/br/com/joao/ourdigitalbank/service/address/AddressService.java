package br.com.joao.ourdigitalbank.service.address;

import br.com.joao.ourdigitalbank.model.address.Address;


public interface AddressService {
    Address saveAddress(Address address, Long clientId) throws Exception;
}
