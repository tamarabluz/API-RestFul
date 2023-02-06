package io.github.tamarabluz.apirestful.controllers;

import io.github.tamarabluz.apirestful.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.services.PeopleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;




@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService service;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid PeopleRequest request){
        var people = request.toModel();
        service.create(people);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(people.getId())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }
}
