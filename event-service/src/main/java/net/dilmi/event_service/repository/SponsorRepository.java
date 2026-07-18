package net.dilmi.event_service.repository;

import net.dilmi.event_service.model.Sponsor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SponsorRepository extends CrudRepository<Sponsor, UUID> {
}
