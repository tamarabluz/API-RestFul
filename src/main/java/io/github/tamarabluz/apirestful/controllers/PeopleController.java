package io.github.tamarabluz.apirestful.controllers;

import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.services.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService service;

    @PostMapping
    public ResponseEntity<PeopleRequest> create(@RequestBody @Valid PeopleRequest people){
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(service.create(people).getId())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }
    @GetMapping
    public ResponseEntity<List<PeopleRequest>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PeopleRequest> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeopleRequest> update(@PathVariable Long id, @RequestBody @Valid PeopleRequest people) {
        service.update(id, people);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PeopleRequest> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
