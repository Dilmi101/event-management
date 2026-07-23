package com.event.analytics.dto;

import lombok.Data;

@Data
public class AnalyticsRequest {

    private String eventType;

    private String page;

    private String pageTitle;

    private String sessionId;

    private String browser;
}
