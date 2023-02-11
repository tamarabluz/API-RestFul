package io.github.tamarabluz.apirestful.services.impl;

import io.github.tamarabluz.apirestful.entities.Address;
import io.github.tamarabluz.apirestful.entities.dtos.request.AddressRequest;
import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.entities.People;
import io.github.tamarabluz.apirestful.repositories.AddressRepository;
import io.github.tamarabluz.apirestful.repositories.PeopleRepository;
import io.github.tamarabluz.apirestful.services.PeopleService;
import io.github.tamarabluz.apirestful.services.exceptions.ObjectNotFoundException;
import io.github.tamarabluz.apirestful.services.exceptions.RuleBusinessException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class PeopleServiceImpl implements PeopleService {
    private final ModelMapper mapper;
    private final PeopleRepository peopleRepository;
    private final AddressRepository addressRepository;


    @Override
    public PeopleRequest create(PeopleRequest peopleRequest) {
        People people = mapper.map(peopleRequest, People.class);
        if (people != null) {
            people = peopleRepository.save(people);
        }
        if (people != null) {
            return mapper.map(people, PeopleRequest.class);
        } else {
            return null;
        }
    }


    @Override
    public List<PeopleRequest> findAll() {
        return peopleRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, PeopleRequest.class))
                .collect(Collectors.toList());
    }

    @Override
    public PeopleRequest findById(Long id) {
        People people = peopleRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );
        return mapper.map(people, PeopleRequest.class);
    }

    @Override
    public PeopleRequest update(Long id, PeopleRequest peopleRequest) {
        People people = peopleRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pessoa com id " + id + " não encontrada."));

        mapper.map(peopleRequest, people);
        people.setId(id);
        return mapper.map(peopleRepository.save(people), PeopleRequest.class);
    }


    @Override
    public void delete(Long id) {
        People people = peopleRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );
        peopleRepository.delete(people);
    }

    @Override
    public PeopleRequest addAddressToPeople(Long id, AddressRequest address) {
        People people = peopleRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );

        Address addressSaved = addressRepository.save(mapper.map(address, Address.class));
        addressSaved.setPeople(People.builder().id(id).build());

        if (people.getAddresses().size() >= 5) {
            throw new RuleBusinessException("Você já preencheu o número máximo de endereços.");
        }

        boolean hasPrincipal = false;
        for (Address existingAddress : people.getAddresses()) {
            if (existingAddress.getIsPrincipal()) {
                hasPrincipal = true;
            }

            if (existingAddress.getLogradouro().equals(addressSaved.getLogradouro())
                    && existingAddress.getNumero().equals(addressSaved.getNumero())) {
                throw new RuleBusinessException("O número da casa e o logradouro já existem no sistema para essa pessoa.");
            }
        }

        if (!hasPrincipal) {
            addressSaved.setIsPrincipal(true);
        }

        people.getAddresses().add(addressSaved);

        People peopleSaved = peopleRepository.save(people);
        return mapper.map(peopleSaved, PeopleRequest.class);
    }




    @Override
    public List<AddressRequest> findAllAddressesToPeople(Long id) {
        peopleRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
        List<Address> addresses = addressRepository.findAddressByPeopleId(id);
        return addresses.stream().map(a -> mapper.map(a, AddressRequest.class)).collect(Collectors.toList());
    }
}



