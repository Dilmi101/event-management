CREATE TABLE IF NOT EXISTS analytics.web_events
(
    timestamp   DateTime DEFAULT now(),
    event_type  String,
    page        String,
    page_title  String DEFAULT '',
    session_id  String,
    browser     String
)
ENGINE = MergeTree()
ORDER BY (timestamp);
