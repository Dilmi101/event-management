package net.dilmi.event_service.repository;

import net.dilmi.event_service.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends CrudRepository<Event, UUID> {
}
