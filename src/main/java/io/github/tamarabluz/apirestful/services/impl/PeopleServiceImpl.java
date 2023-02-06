package io.github.tamarabluz.apirestful.services.impl;

import io.github.tamarabluz.apirestful.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.entities.People;
import io.github.tamarabluz.apirestful.repositories.PeopleRepository;
import io.github.tamarabluz.apirestful.services.PeopleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PeopleServiceImpl implements PeopleService {
    private final ModelMapper mapper;
    private final PeopleRepository peopleRepository;

    public PeopleServiceImpl(ModelMapper mapper, PeopleRepository peopleRepository) {
        this.mapper = mapper;
        this.peopleRepository = peopleRepository;
    }

            @Transactional
            public People create (People people){
                return peopleRepository.save(people);
            }
    }


