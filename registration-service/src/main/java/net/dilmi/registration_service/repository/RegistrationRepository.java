package net.dilmi.registration_service.repository;

import net.dilmi.registration_service.model.Registration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, UUID> {
}
