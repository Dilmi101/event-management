package com.event.analytics.controller;

import com.event.analytics.dto.AnalyticsRequest;
import com.event.analytics.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AnalyticsRequest request) {

        analyticsService.save(request);

        return ResponseEntity.ok().build();
    }
}
