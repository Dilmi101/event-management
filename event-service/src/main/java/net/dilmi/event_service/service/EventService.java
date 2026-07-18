package net.dilmi.event_service.service;

import net.dilmi.event_service.model.Event;
import net.dilmi.event_service.repository.EventRepository;
import org.springframework.stereotype.Service;

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

    public Event createEvent(Event event) {
        event.setEventId(UUID.randomUUID());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public Event updateEvent(UUID eventId, Event updatedEvent) {
        updatedEvent.setEventId(eventId);
        updatedEvent.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(updatedEvent);
    }

    public void deleteEvent(UUID eventId) {
        eventRepository.deleteById(eventId);
    }
}
