package net.dilmi.program_service.controller;

import net.dilmi.program_service.model.Speaker;
import net.dilmi.program_service.service.SpeakerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/speakers")
public class SpeakerController {

    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public ResponseEntity<List<Speaker>> getAllSpeakers() {
        return ResponseEntity.ok(speakerService.getAllSpeakers());
    }

    @GetMapping("/{speakerId}")
    public ResponseEntity<Speaker> getSpeakerById(@PathVariable UUID speakerId) {
        return speakerService.getSpeakerById(speakerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Speaker> createSpeaker(@RequestBody Speaker speaker) {
        Speaker created = speakerService.createSpeaker(speaker);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{speakerId}")
    public ResponseEntity<Speaker> updateSpeaker(@PathVariable UUID speakerId, @RequestBody Speaker speaker) {
        try {
            Speaker updated = speakerService.updateSpeaker(speakerId, speaker);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{speakerId}")
    public ResponseEntity<Void> deleteSpeaker(@PathVariable UUID speakerId) {
        speakerService.deleteSpeaker(speakerId);
        return ResponseEntity.noContent().build();
    }
}
