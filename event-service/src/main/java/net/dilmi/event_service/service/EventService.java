package net.dilmi.event_service.service;

import net.dilmi.event_service.model.Event;
import net.dilmi.event_service.notification.LowSeatsNotifier;
import net.dilmi.event_service.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final LowSeatsNotifier lowSeatsNotifier;

    @Value("${notifications.low-seats.threshold:10}")
    private int lowSeatsThreshold;

    public EventService(EventRepository eventRepository, LowSeatsNotifier lowSeatsNotifier) {
        this.eventRepository = eventRepository;
        this.lowSeatsNotifier = lowSeatsNotifier;
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

        Event updated = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found after reservation: " + eventId));

        try {
            if (updated.getSeatsAvailable() != null && updated.getSeatsAvailable() < lowSeatsThreshold) {
                int notifyRows = eventRepository.markLowSeatsNotified(eventId);
                if (notifyRows == 1) {
                    boolean sent = lowSeatsNotifier.notifyLowSeats(eventId, updated.getSeatsAvailable(), lowSeatsThreshold);
                    if (!sent) {
                        // Let a future reservation retry instead of permanently burning
                        // this event's one notification on a failed invoke.
                        eventRepository.clearLowSeatsNotified(eventId);
                    }
                }
            }
        } catch (Exception e) {
            // Notification/debounce failures must never fail the reservation itself.
            log.error("Low-seats notification check failed for event {}: {}", eventId, e.getMessage(), e);
        }

        return updated;
    }

    @Transactional
    public Event releaseSeats(UUID eventId, int ticketCount) {
        eventRepository.releaseSeats(eventId, ticketCount);
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
    }
}
