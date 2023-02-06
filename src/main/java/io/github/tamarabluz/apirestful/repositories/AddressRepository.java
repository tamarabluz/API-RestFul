package io.github.tamarabluz.apirestful.repositories;

import io.github.tamarabluz.apirestful.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByPersonId(Long id);
}
