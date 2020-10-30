package ru.ekovtun.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ekovtun.entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
