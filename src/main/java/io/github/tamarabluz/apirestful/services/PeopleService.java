package io.github.tamarabluz.apirestful.services;

import io.github.tamarabluz.apirestful.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.entities.People;

public interface PeopleService {

    People create(People people);
}
