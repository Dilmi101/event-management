package net.dilmi.registration_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("registrations")
public class Registration {

    @Id
    private UUID registrationId;

    @Column("event_id")
    private UUID eventId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String phone;
    private String email;

    @Column("ticket_count")
    private Integer ticketCount;

    @Column("registered_at")
    private LocalDateTime registeredAt;

    public Registration() {}

    public Registration(UUID registrationId, UUID eventId, String firstName, String lastName,
                        String phone, String email, Integer ticketCount, LocalDateTime registeredAt) {
        this.registrationId = registrationId;
        this.eventId = eventId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.ticketCount = ticketCount;
        this.registeredAt = registeredAt;
    }

    public UUID getRegistrationId() { return registrationId; }
    public void setRegistrationId(UUID registrationId) { this.registrationId = registrationId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getTicketCount() { return ticketCount; }
    public void setTicketCount(Integer ticketCount) { this.ticketCount = ticketCount; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
}
