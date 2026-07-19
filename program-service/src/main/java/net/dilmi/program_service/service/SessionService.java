package net.dilmi.program_service.service;

import net.dilmi.program_service.model.Session;
import net.dilmi.program_service.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllSessions() {
        return (List<Session>) sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Transactional
    public Session createSession(Session session) {
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    @Transactional
    public Session updateSession(UUID sessionId, Session updatedSession) {
        Session existing = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found: " + sessionId));
        existing.setTrackId(updatedSession.getTrackId());
        existing.setEventId(updatedSession.getEventId());
        existing.setDay(updatedSession.getDay());
        existing.setSessionTitle(updatedSession.getSessionTitle());
        existing.setSpeakerName(updatedSession.getSpeakerName());
        existing.setStartTime(updatedSession.getStartTime());
        existing.setEndTime(updatedSession.getEndTime());
        existing.setRoom(updatedSession.getRoom());
        return sessionRepository.save(existing);
    }

    @Transactional
    public void deleteSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
