package net.dilmi.program_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("speakers")
public class Speaker {

    @Id
    private UUID speakerId;

    @Column("event_id")
    private UUID eventId;

    private String name;
    private String role;
    private String bio;

    @Column("photo_url")
    private String photoUrl;

    @Column("created_at")
    private LocalDateTime createdAt;

    public Speaker() {}

    public Speaker(UUID speakerId, UUID eventId, String name, String role, String bio, String photoUrl, LocalDateTime createdAt) {
        this.speakerId = speakerId;
        this.eventId = eventId;
        this.name = name;
        this.role = role;
        this.bio = bio;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
    }

    public UUID getSpeakerId() { return speakerId; }
    public void setSpeakerId(UUID speakerId) { this.speakerId = speakerId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
