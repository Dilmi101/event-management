package net.dilmi.program_service.repository;

import net.dilmi.program_service.model.Speaker;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpeakerRepository extends CrudRepository<Speaker, UUID> {
}
