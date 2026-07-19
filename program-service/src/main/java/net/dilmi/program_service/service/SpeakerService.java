package net.dilmi.program_service.service;

import net.dilmi.program_service.model.Speaker;
import net.dilmi.program_service.repository.SpeakerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public List<Speaker> getAllSpeakers() {
        return (List<Speaker>) speakerRepository.findAll();
    }

    public Optional<Speaker> getSpeakerById(UUID speakerId) {
        return speakerRepository.findById(speakerId);
    }

    @Transactional
    public Speaker createSpeaker(Speaker speaker) {
        speaker.setCreatedAt(LocalDateTime.now());
        return speakerRepository.save(speaker);
    }

    @Transactional
    public Speaker updateSpeaker(UUID speakerId, Speaker updatedSpeaker) {
        Speaker existing = speakerRepository.findById(speakerId)
                .orElseThrow(() -> new RuntimeException("Speaker not found: " + speakerId));
        existing.setEventId(updatedSpeaker.getEventId());
        existing.setName(updatedSpeaker.getName());
        existing.setRole(updatedSpeaker.getRole());
        existing.setBio(updatedSpeaker.getBio());
        existing.setPhotoUrl(updatedSpeaker.getPhotoUrl());
        return speakerRepository.save(existing);
    }

    @Transactional
    public void deleteSpeaker(UUID speakerId) {
        speakerRepository.deleteById(speakerId);
    }
}
