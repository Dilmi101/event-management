package net.dilmi.event_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("contact_messages")
public class ContactMessage {

    @Id
    private UUID messageId;

    private String name;
    private String email;
    private String message;

    @Column("received_at")
    private LocalDateTime receivedAt;

    public ContactMessage() {}

    public ContactMessage(UUID messageId, String name, String email, String message, LocalDateTime receivedAt) {
        this.messageId = messageId;
        this.name = name;
        this.email = email;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public UUID getMessageId() { return messageId; }
    public void setMessageId(UUID messageId) { this.messageId = messageId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
}
