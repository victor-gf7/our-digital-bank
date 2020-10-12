package br.com.joao.ourdigitalbank.repository.address;

import br.com.joao.ourdigitalbank.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
