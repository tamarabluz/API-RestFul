package io.github.tamarabluz.apirestful.services.impl;

import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.entities.People;
import io.github.tamarabluz.apirestful.repositories.PeopleRepository;
import io.github.tamarabluz.apirestful.services.PeopleService;
import io.github.tamarabluz.apirestful.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PeopleServiceImpl implements PeopleService {
    private final ModelMapper mapper;
    private final PeopleRepository peopleRepository;

    public PeopleServiceImpl(ModelMapper mapper, PeopleRepository peopleRepository) {
        this.mapper = mapper;
        this.peopleRepository = peopleRepository;
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
        people.setId(id);return mapper.map(
                peopleRepository.save(mapper.map(people, People.class)), PeopleRequest.class);
    }
    @Override
    public void delete(Long id) {
        People people = peopleRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );
        peopleRepository.delete(people);
    }


}



