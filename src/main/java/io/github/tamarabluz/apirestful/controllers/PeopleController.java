package io.github.tamarabluz.apirestful.controllers;

import io.github.tamarabluz.apirestful.entities.dtos.request.AddressRequest;
import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.services.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping(produces="application/json", consumes="application/json")
    @Operation(summary = "Created Person.")
    public ResponseEntity<PeopleRequest> create(@RequestBody @Valid PeopleRequest people) {
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(service.create(people).getId())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(produces="application/json")
    @Operation(summary = "Return all People.")
    public ResponseEntity<List<PeopleRequest>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Return one person by Id.")
    public ResponseEntity<PeopleRequest> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes="application/json")
    @Operation(summary = "Update person by Id.")
    public ResponseEntity<PeopleRequest> update(@PathVariable Long id, @RequestBody @Valid PeopleRequest people) {
        service.update(id, people);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Delete person by Id.")
    public ResponseEntity<PeopleRequest> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/add-address", produces = "application/json", consumes="application/json")
    @Operation(summary = "Add address for person.")
    public ResponseEntity<PeopleRequest> addAddressToPeopple(@PathVariable("id") Long id, @RequestBody @Valid AddressRequest address) {
        service.addAddressToPeople(id, address);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/addresses", produces = "application/json")
    public ResponseEntity<List<AddressRequest>> findAllAddressToPeople(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findAllAddressesToPeople(id));
    }
}