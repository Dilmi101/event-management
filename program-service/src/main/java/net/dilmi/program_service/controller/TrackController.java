package net.dilmi.program_service.controller;

import net.dilmi.program_service.model.Track;
import net.dilmi.program_service.service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<Track>> getAllTracks() {
        return ResponseEntity.ok(trackService.getAllTracks());
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<Track> getTrackById(@PathVariable UUID trackId) {
        return trackService.getTrackById(trackId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        Track created = trackService.createTrack(track);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{trackId}")
    public ResponseEntity<Track> updateTrack(@PathVariable UUID trackId, @RequestBody Track track) {
        trackService.updateTrack(trackId, track);
        return trackService.getTrackById(trackId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable UUID trackId) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.noContent().build();
    }
}
