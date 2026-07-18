package net.dilmi.program_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Table("sessions")
public class Session {

    @Id
    private UUID sessionId;

    @Column("track_id")
    private UUID trackId;

    @Column("event_id")
    private UUID eventId;

    private Integer day;

    @Column("session_title")
    private String sessionTitle;

    @Column("speaker_name")
    private String speakerName;

    @Column("start_time")
    private LocalTime startTime;

    @Column("end_time")
    private LocalTime endTime;

    private String room;

    @Column("created_at")
    private LocalDateTime createdAt;

    public Session() {}

    public Session(UUID sessionId, UUID trackId, UUID eventId, Integer day,
                   String sessionTitle, String speakerName, LocalTime startTime,
                   LocalTime endTime, String room, LocalDateTime createdAt) {
        this.sessionId = sessionId;
        this.trackId = trackId;
        this.eventId = eventId;
        this.day = day;
        this.sessionTitle = sessionTitle;
        this.speakerName = speakerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.createdAt = createdAt;
    }

    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }

    public UUID getTrackId() { return trackId; }
    public void setTrackId(UUID trackId) { this.trackId = trackId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public Integer getDay() { return day; }
    public void setDay(Integer day) { this.day = day; }

    public String getSessionTitle() { return sessionTitle; }
    public void setSessionTitle(String sessionTitle) { this.sessionTitle = sessionTitle; }

    public String getSpeakerName() { return speakerName; }
    public void setSpeakerName(String speakerName) { this.speakerName = speakerName; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
