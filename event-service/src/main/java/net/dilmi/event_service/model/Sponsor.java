package net.dilmi.event_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("sponsors")
public class Sponsor {

    @Id
    private UUID sponsorId;

    @Column("event_id")
    private UUID eventId;

    private String name;

    @Column("logo_url")
    private String logoUrl;

    @Column("created_at")
    private LocalDateTime createdAt;

    public Sponsor() {}

    public Sponsor(UUID sponsorId, UUID eventId, String name, String logoUrl, LocalDateTime createdAt) {
        this.sponsorId = sponsorId;
        this.eventId = eventId;
        this.name = name;
        this.logoUrl = logoUrl;
        this.createdAt = createdAt;
    }

    public UUID getSponsorId() { return sponsorId; }
    public void setSponsorId(UUID sponsorId) { this.sponsorId = sponsorId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
