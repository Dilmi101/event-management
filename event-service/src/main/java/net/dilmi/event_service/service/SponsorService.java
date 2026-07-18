package net.dilmi.event_service.service;

import net.dilmi.event_service.model.Sponsor;
import net.dilmi.event_service.repository.SponsorRepository;
import org.springframework.stereotype.Service;

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

    public Sponsor createSponsor(Sponsor sponsor) {
        sponsor.setSponsorId(UUID.randomUUID());
        sponsor.setCreatedAt(LocalDateTime.now());
        return sponsorRepository.save(sponsor);
    }

    public Sponsor updateSponsor(UUID sponsorId, Sponsor updatedSponsor) {
        updatedSponsor.setSponsorId(sponsorId);
        return sponsorRepository.save(updatedSponsor);
    }

    public void deleteSponsor(UUID sponsorId) {
        sponsorRepository.deleteById(sponsorId);
    }
}
