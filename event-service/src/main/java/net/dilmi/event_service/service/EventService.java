package net.dilmi.event_service.service;

import net.dilmi.event_service.model.Event;
import net.dilmi.event_service.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    public Optional<Event> getEventById(UUID eventId) {
        return eventRepository.findById(eventId);
    }

    @Transactional
    public Event createEvent(Event event) {
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(UUID eventId, Event updatedEvent) {
        Event existing = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setVenueName(updatedEvent.getVenueName());
        existing.setVenueStreet(updatedEvent.getVenueStreet());
        existing.setVenueCity(updatedEvent.getVenueCity());
        existing.setVenuePhone(updatedEvent.getVenuePhone());
        existing.setEventDate(updatedEvent.getEventDate());
        existing.setEventTime(updatedEvent.getEventTime());
        existing.setTicketPrice(updatedEvent.getTicketPrice());
        existing.setCapacity(updatedEvent.getCapacity());
        existing.setSeatsAvailable(updatedEvent.getSeatsAvailable());
        existing.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(existing);
    }

    @Transactional
    public void deleteEvent(UUID eventId) {
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public Event reserveSeats(UUID eventId, int ticketCount) {
        int updatedRows = eventRepository.reserveSeats(eventId, ticketCount);
        if (updatedRows == 0) {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
            if (event.getSeatsAvailable() < ticketCount) {
                throw new RuntimeException("Not enough seats available. Requested: " + ticketCount
                        + ", Available: " + event.getSeatsAvailable());
            }
            throw new RuntimeException("Failed to reserve seats for event: " + eventId);
        }
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found after reservation: " + eventId));
    }

    @Transactional
    public Event releaseSeats(UUID eventId, int ticketCount) {
        eventRepository.releaseSeats(eventId, ticketCount);
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
    }
}
