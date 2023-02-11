package io.github.tamarabluz.apirestful.services;

import io.github.tamarabluz.apirestful.entities.dtos.request.AddressRequest;
import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;

import java.util.List;


public interface PeopleService {

    PeopleRequest create(PeopleRequest people);

    List<PeopleRequest> findAll();

    PeopleRequest findById(Long id);

    PeopleRequest update(Long id, PeopleRequest people);

    void delete(Long id);

   PeopleRequest addAddressToPeople(Long id, AddressRequest address);

    List<AddressRequest> findAllAddressesToPeople(Long id);
}
