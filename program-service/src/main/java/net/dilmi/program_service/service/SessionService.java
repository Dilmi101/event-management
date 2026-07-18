package net.dilmi.program_service.service;

import net.dilmi.program_service.model.Session;
import net.dilmi.program_service.repository.SessionRepository;
import org.springframework.stereotype.Service;

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

    public Session createSession(Session session) {
        session.setSessionId(UUID.randomUUID());
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    public Session updateSession(UUID sessionId, Session updatedSession) {
        updatedSession.setSessionId(sessionId);
        return sessionRepository.save(updatedSession);
    }

    public void deleteSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
