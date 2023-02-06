package io.github.tamarabluz.apirestful.services.impl;

import io.github.tamarabluz.apirestful.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.entities.People;
import io.github.tamarabluz.apirestful.repositories.PeopleRepository;
import io.github.tamarabluz.apirestful.services.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


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
}


