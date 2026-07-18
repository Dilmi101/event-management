package net.dilmi.program_service.repository;

import net.dilmi.program_service.model.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackRepository extends CrudRepository<Track, UUID> {
}
