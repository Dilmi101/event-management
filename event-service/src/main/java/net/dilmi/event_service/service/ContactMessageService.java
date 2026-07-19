package net.dilmi.event_service.service;

import net.dilmi.event_service.model.ContactMessage;
import net.dilmi.event_service.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public List<ContactMessage> getAllMessages() {
        return (List<ContactMessage>) contactMessageRepository.findAll();
    }

    public Optional<ContactMessage> getMessageById(UUID messageId) {
        return contactMessageRepository.findById(messageId);
    }

    @Transactional
    public ContactMessage createMessage(ContactMessage message) {
        message.setReceivedAt(LocalDateTime.now());
        return contactMessageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(UUID messageId) {
        contactMessageRepository.deleteById(messageId);
    }
}
