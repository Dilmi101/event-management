package com.event.analytics.service;

import com.event.analytics.dto.AnalyticsRequest;
import com.event.analytics.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repository;

    public AnalyticsService(AnalyticsRepository repository) {
        this.repository = repository;
    }

    public void save(AnalyticsRequest request) {

        repository.save(
                request.getEventType(),
                request.getPage(),
                request.getSessionId(),
                request.getBrowser()
        );
    }
}
