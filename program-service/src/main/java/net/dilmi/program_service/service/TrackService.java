package net.dilmi.program_service.service;

import net.dilmi.program_service.model.Track;
import net.dilmi.program_service.repository.TrackRepository;
import org.springframework.stereotype.Service;

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

    public Track createTrack(Track track) {
        track.setTrackId(UUID.randomUUID());
        track.setCreatedAt(LocalDateTime.now());
        return trackRepository.save(track);
    }

    public Track updateTrack(UUID trackId, Track updatedTrack) {
        updatedTrack.setTrackId(trackId);
        return trackRepository.save(updatedTrack);
    }

    public void deleteTrack(UUID trackId) {
        trackRepository.deleteById(trackId);
    }
}
