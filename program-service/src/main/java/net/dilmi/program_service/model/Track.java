package net.dilmi.program_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("tracks")
public class Track {

    @Id
    private UUID trackId;
    private UUID eventId;
    private String name;

    @Column("created_at")
    private LocalDateTime createdAt;

    public Track() {}

    public Track(UUID trackId, UUID eventId, String name, LocalDateTime createdAt) {
        this.trackId = trackId;
        this.eventId = eventId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getTrackId() { return trackId; }
    public void setTrackId(UUID trackId) { this.trackId = trackId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
