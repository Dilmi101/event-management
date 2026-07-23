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
    private String description;

    @Column("venue_name")
    private String venueName;

    @Column("venue_street")
    private String venueStreet;

    @Column("venue_city")
    private String venueCity;

    @Column("venue_phone")
    private String venuePhone;

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

    @Column("low_seats_notified_at")
    private LocalDateTime lowSeatsNotifiedAt;

    public Event() {}

    public Event(UUID eventId, String title, String description, String venueName,
                 String venueStreet, String venueCity, String venuePhone,
                 LocalDate eventDate, LocalTime eventTime, BigDecimal ticketPrice,
                 Integer capacity, Integer seatsAvailable,
                 LocalDateTime createdAt, LocalDateTime updatedAt,
                 LocalDateTime lowSeatsNotifiedAt) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.venueName = venueName;
        this.venueStreet = venueStreet;
        this.venueCity = venueCity;
        this.venuePhone = venuePhone;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.seatsAvailable = seatsAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lowSeatsNotifiedAt = lowSeatsNotifiedAt;
    }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    public String getVenueStreet() { return venueStreet; }
    public void setVenueStreet(String venueStreet) { this.venueStreet = venueStreet; }

    public String getVenueCity() { return venueCity; }
    public void setVenueCity(String venueCity) { this.venueCity = venueCity; }

    public String getVenuePhone() { return venuePhone; }
    public void setVenuePhone(String venuePhone) { this.venuePhone = venuePhone; }

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

    public LocalDateTime getLowSeatsNotifiedAt() { return lowSeatsNotifiedAt; }
    public void setLowSeatsNotifiedAt(LocalDateTime lowSeatsNotifiedAt) { this.lowSeatsNotifiedAt = lowSeatsNotifiedAt; }
}
