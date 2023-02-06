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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class PeopleServiceImpl implements PeopleService {
    private final ModelMapper mapper;
    private final PeopleRepository peopleRepository;
    private final AddressRepository addressRepository;


    public PeopleServiceImpl(ModelMapper mapper, PeopleRepository peopleRepository, AddressRepository addressRepository) {
        this.mapper = mapper;
        this.peopleRepository = peopleRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public PeopleRequest create(PeopleRequest people) {
        return mapper.map(
                peopleRepository.save(mapper.map(people, People.class)),
                PeopleRequest.class);
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
        return mapper.map(
                peopleRepository.findById(id).orElseThrow(
                        () -> new ObjectNotFoundException("Objeto não encontrado")
                ),
                PeopleRequest.class);
    }

    @Override
    public PeopleRequest update(Long id, PeopleRequest people) {
        peopleRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado"));
        people.setId(id);
        return mapper.map(
                peopleRepository.save(mapper.map(people, People.class)), PeopleRequest.class);
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

        if (people.getAddresses().size() <= 4) {
            if (people.getAddresses().isEmpty()) {
                addressSaved.setIsPrincipal(true);
            }
            for (int i = 0; i < people.getAddresses().size(); i++) {
                if (people.getAddresses().get(i).getIsPrincipal() && addressSaved.getIsPrincipal()) {
                    people.getAddresses().get(i).setIsPrincipal(false);
                }
                if (people.getAddresses().get(i).getLogradouro().equals(addressSaved.getLogradouro())
                        && people.getAddresses().get(i).getNumero().equals(addressSaved.getNumero())) {
                    throw new RuleBusinessException("O número da casa e o logradouro já existe no sistema para essa pessoa.");
                }
            }
        } else {
            throw new RuleBusinessException("Você já preencheu o número máximo de endereços.");
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



