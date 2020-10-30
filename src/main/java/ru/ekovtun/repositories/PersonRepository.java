package ru.ekovtun.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ekovtun.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    
}
