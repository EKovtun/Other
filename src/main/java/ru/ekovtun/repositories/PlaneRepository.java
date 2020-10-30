package ru.ekovtun.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ekovtun.entities.Plane;

@Repository
public interface PlaneRepository extends CrudRepository<Plane, Long> {}
