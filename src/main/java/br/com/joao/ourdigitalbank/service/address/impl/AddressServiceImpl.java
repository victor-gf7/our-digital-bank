package br.com.joao.ourdigitalbank.service.address.impl;

import br.com.joao.ourdigitalbank.model.address.Address;
import br.com.joao.ourdigitalbank.repository.address.AddressRepository;
import br.com.joao.ourdigitalbank.repository.client.ClientRepository;
import br.com.joao.ourdigitalbank.service.address.AddressService;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    public AddressServiceImpl(AddressRepository addressRepository, ClientRepository clientRepository) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }


//    @Override
//    public ResponseEntity<Address> saveAddress(AddressRequest addressRequest) throws Exception {
//
//        var address = new Address();
//        Client client = new Client();
//        HttpHeaders responseHeaders = new HttpHeaders();
//        var c  = clientRepository.findById(addressRequest.getClientId());
//
//        if(c.isPresent()){
//            var clientSave = c.get();
//            address.setZipCode(addressRequest.getZipCode());
//            address.setStreet(addressRequest.getStreet());
//            address.setNeighborhood(addressRequest.getNeighborhood());
//            address.setComplement(addressRequest.getComplement());
//            address.setCity(addressRequest.getCity());
//            address.setState(addressRequest.getState());
//
//            clientSave.setAddress(address);
//
//            try {
//                addressRepository.save(address);
//                clientRepository.save(clientSave);
//                responseHeaders.setLocation (new URI("/ourbank/upload/"));
//
//                return new ResponseEntity<>(address,responseHeaders, HttpStatus.CREATED);
//            } catch (Exception e){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }else{
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        }
//    }

    @Override
    public Address saveAddress(Address address, Long clientId) throws Exception {

        var c  = clientRepository.findById(clientId);

        if(c.isPresent()){
            var clientSave = c.get();
            clientSave.setAddress(address);
            try{
                return clientRepository.save(clientSave).getAddress();
            } catch (Exception e){
                return null;
            }
        } else{
            return null;
        }
    }
}
