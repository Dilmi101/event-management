package net.dilmi.event_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Table("events")
public class Event {

    @Id
    private UUID eventId;
    private String title;
    private String venue;

    @Column("event_date")
    private LocalDate eventDate;

    @Column("event_time")
    private LocalTime eventTime;

    @Column("ticket_price")
    private BigDecimal ticketPrice;

    private Integer capacity;

    @Column("seats_available")
    private Integer seatsAvailable;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    public Event() {}

    public Event(UUID eventId, String title, String venue, LocalDate eventDate,
                 LocalTime eventTime, BigDecimal ticketPrice, Integer capacity,
                 Integer seatsAvailable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.eventId = eventId;
        this.title = title;
        this.venue = venue;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.seatsAvailable = seatsAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getEventTime() { return eventTime; }
    public void setEventTime(LocalTime eventTime) { this.eventTime = eventTime; }

    public BigDecimal getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(Integer seatsAvailable) { this.seatsAvailable = seatsAvailable; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
