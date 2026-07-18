package net.dilmi.event_service.repository;

import net.dilmi.event_service.model.ContactMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContactMessageRepository extends CrudRepository<ContactMessage, UUID> {
}
