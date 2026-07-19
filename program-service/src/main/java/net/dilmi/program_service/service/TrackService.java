package net.dilmi.program_service.service;

import net.dilmi.program_service.model.Track;
import net.dilmi.program_service.repository.TrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrackService {

    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getAllTracks() {
        return (List<Track>) trackRepository.findAll();
    }

    public Optional<Track> getTrackById(UUID trackId) {
        return trackRepository.findById(trackId);
    }

    @Transactional
    public Track createTrack(Track track) {
        track.setCreatedAt(LocalDateTime.now());
        return trackRepository.save(track);
    }

    @Transactional
    public Track updateTrack(UUID trackId, Track updatedTrack) {
        Track existing = trackRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Track not found: " + trackId));
        existing.setEventId(updatedTrack.getEventId());
        existing.setName(updatedTrack.getName());
        return trackRepository.save(existing);
    }

    @Transactional
    public void deleteTrack(UUID trackId) {
        trackRepository.deleteById(trackId);
    }
}
