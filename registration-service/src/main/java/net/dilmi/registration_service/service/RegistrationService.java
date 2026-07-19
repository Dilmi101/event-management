package net.dilmi.registration_service.service;

import net.dilmi.event_service.grpc.EventServiceGrpc;
import net.dilmi.event_service.grpc.ReserveSeatsRequest;
import net.dilmi.event_service.grpc.ReleaseSeatsRequest;
import net.dilmi.registration_service.model.Registration;
import net.dilmi.registration_service.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventServiceGrpc.EventServiceBlockingStub eventServiceStub;

    public RegistrationService(RegistrationRepository registrationRepository,
                               EventServiceGrpc.EventServiceBlockingStub eventServiceStub) {
        this.registrationRepository = registrationRepository;
        this.eventServiceStub = eventServiceStub;
    }

    public List<Registration> getAllRegistrations() {
        return (List<Registration>) registrationRepository.findAll();
    }

    public Optional<Registration> getRegistrationById(UUID registrationId) {
        return registrationRepository.findById(registrationId);
    }

    @Transactional
    public Registration createRegistration(Registration registration) {
        ReserveSeatsRequest request = ReserveSeatsRequest.newBuilder()
                .setEventId(registration.getEventId().toString())
                .setTicketCount(registration.getTicketCount())
                .build();
        eventServiceStub.reserveSeats(request);
        registration.setRegisteredAt(LocalDateTime.now());
        return registrationRepository.save(registration);
    }

    @Transactional
    public Registration updateRegistration(UUID registrationId, Registration updatedRegistration) {
        Registration existing = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found: " + registrationId));
        int oldTicketCount = existing.getTicketCount();
        int newTicketCount = updatedRegistration.getTicketCount();
        int diff = newTicketCount - oldTicketCount;
        if (diff > 0) {
            ReserveSeatsRequest request = ReserveSeatsRequest.newBuilder()
                    .setEventId(existing.getEventId().toString())
                    .setTicketCount(diff)
                    .build();
            eventServiceStub.reserveSeats(request);
        } else if (diff < 0) {
            ReleaseSeatsRequest request = ReleaseSeatsRequest.newBuilder()
                    .setEventId(existing.getEventId().toString())
                    .setTicketCount(-diff)
                    .build();
            eventServiceStub.releaseSeats(request);
        }
        existing.setEventId(updatedRegistration.getEventId());
        existing.setFirstName(updatedRegistration.getFirstName());
        existing.setLastName(updatedRegistration.getLastName());
        existing.setPhone(updatedRegistration.getPhone());
        existing.setEmail(updatedRegistration.getEmail());
        existing.setTicketCount(updatedRegistration.getTicketCount());
        return registrationRepository.save(existing);
    }

    @Transactional
    public void deleteRegistration(UUID registrationId) {
        Registration existing = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found: " + registrationId));
        ReleaseSeatsRequest request = ReleaseSeatsRequest.newBuilder()
                .setEventId(existing.getEventId().toString())
                .setTicketCount(existing.getTicketCount())
                .build();
        eventServiceStub.releaseSeats(request);
        registrationRepository.deleteById(registrationId);
    }
}
