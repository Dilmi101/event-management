package net.dilmi.event_service.service;

import net.dilmi.event_service.model.Sponsor;
import net.dilmi.event_service.repository.SponsorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SponsorService {

    private final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public List<Sponsor> getAllSponsors() {
        return (List<Sponsor>) sponsorRepository.findAll();
    }

    public Optional<Sponsor> getSponsorById(UUID sponsorId) {
        return sponsorRepository.findById(sponsorId);
    }

    @Transactional
    public Sponsor createSponsor(Sponsor sponsor) {
        sponsor.setCreatedAt(LocalDateTime.now());
        return sponsorRepository.save(sponsor);
    }

    @Transactional
    public Sponsor updateSponsor(UUID sponsorId, Sponsor updatedSponsor) {
        Sponsor existing = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new RuntimeException("Sponsor not found: " + sponsorId));
        existing.setEventId(updatedSponsor.getEventId());
        existing.setName(updatedSponsor.getName());
        existing.setLogoUrl(updatedSponsor.getLogoUrl());
        return sponsorRepository.save(existing);
    }

    @Transactional
    public void deleteSponsor(UUID sponsorId) {
        sponsorRepository.deleteById(sponsorId);
    }
}
