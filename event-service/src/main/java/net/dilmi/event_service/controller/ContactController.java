package net.dilmi.event_service.controller;

import net.dilmi.event_service.model.ContactMessage;
import net.dilmi.event_service.service.ContactMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageService contactMessageService;

    public ContactController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<ContactMessage> getMessageById(@PathVariable UUID messageId) {
        return contactMessageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContactMessage> createMessage(@RequestBody ContactMessage message) {
        ContactMessage created = contactMessageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
        contactMessageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}
