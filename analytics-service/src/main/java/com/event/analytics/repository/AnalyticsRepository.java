package com.event.analytics.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnalyticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnalyticsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(String eventType,
                     String page,
                     String sessionId,
                     String browser) {

        String sql = """
            INSERT INTO web_events
            (timestamp,event_type,page,session_id,browser)
            VALUES (now(),?,?,?,?)
            """;

        jdbcTemplate.update(
                sql,
                eventType,
                page,
                sessionId,
                browser
        );
    }
}
