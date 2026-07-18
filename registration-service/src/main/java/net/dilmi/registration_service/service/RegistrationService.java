package net.dilmi.registration_service.service;

import net.dilmi.registration_service.model.Registration;
import net.dilmi.registration_service.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<Registration> getAllRegistrations() {
        return (List<Registration>) registrationRepository.findAll();
    }

    public Optional<Registration> getRegistrationById(UUID registrationId) {
        return registrationRepository.findById(registrationId);
    }

    public Registration createRegistration(Registration registration) {
        registration.setRegistrationId(UUID.randomUUID());
        registration.setRegisteredAt(LocalDateTime.now());
        return registrationRepository.save(registration);
    }

    public Registration updateRegistration(UUID registrationId, Registration updatedRegistration) {
        updatedRegistration.setRegistrationId(registrationId);
        return registrationRepository.save(updatedRegistration);
    }

    public void deleteRegistration(UUID registrationId) {
        registrationRepository.deleteById(registrationId);
    }
}
