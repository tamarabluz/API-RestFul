package io.github.tamarabluz.apirestful.repositories;

import io.github.tamarabluz.apirestful.entities.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
}
