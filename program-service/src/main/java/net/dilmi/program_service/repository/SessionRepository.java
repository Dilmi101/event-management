package net.dilmi.program_service.repository;

import net.dilmi.program_service.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {
}
