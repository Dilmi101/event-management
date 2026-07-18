package net.dilmi.program_service.service;

import net.dilmi.program_service.model.Speaker;
import net.dilmi.program_service.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

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

    public Speaker createSpeaker(Speaker speaker) {
        speaker.setSpeakerId(UUID.randomUUID());
        speaker.setCreatedAt(LocalDateTime.now());
        return speakerRepository.save(speaker);
    }

    public Speaker updateSpeaker(UUID speakerId, Speaker updatedSpeaker) {
        updatedSpeaker.setSpeakerId(speakerId);
        return speakerRepository.save(updatedSpeaker);
    }

    public void deleteSpeaker(UUID speakerId) {
        speakerRepository.deleteById(speakerId);
    }
}
