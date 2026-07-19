package net.dilmi.registration_service.controller;

import net.dilmi.registration_service.model.Registration;
import net.dilmi.registration_service.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable UUID registrationId) {
        return registrationService.getRegistrationById(registrationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration registration) {
        Registration created = registrationService.createRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<Registration> updateRegistration(@PathVariable UUID registrationId, @RequestBody Registration registration) {
        try {
            Registration updated = registrationService.updateRegistration(registrationId, registration);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable UUID registrationId) {
        registrationService.deleteRegistration(registrationId);
        return ResponseEntity.noContent().build();
    }
}
