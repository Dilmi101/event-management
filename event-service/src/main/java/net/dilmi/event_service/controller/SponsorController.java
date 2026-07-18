package net.dilmi.event_service.controller;

import net.dilmi.event_service.model.Sponsor;
import net.dilmi.event_service.service.SponsorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping
    public ResponseEntity<List<Sponsor>> getAllSponsors() {
        return ResponseEntity.ok(sponsorService.getAllSponsors());
    }

    @GetMapping("/{sponsorId}")
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable UUID sponsorId) {
        return sponsorService.getSponsorById(sponsorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sponsor> createSponsor(@RequestBody Sponsor sponsor) {
        Sponsor created = sponsorService.createSponsor(sponsor);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{sponsorId}")
    public ResponseEntity<Sponsor> updateSponsor(@PathVariable UUID sponsorId, @RequestBody Sponsor sponsor) {
        sponsorService.updateSponsor(sponsorId, sponsor);
        return sponsorService.getSponsorById(sponsorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{sponsorId}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable UUID sponsorId) {
        sponsorService.deleteSponsor(sponsorId);
        return ResponseEntity.noContent().build();
    }
}
