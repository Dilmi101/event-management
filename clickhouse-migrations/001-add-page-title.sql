-- Every statement in this directory must be safe to re-run against an already-migrated
-- table (no migration-tracking table exists) -- use IF NOT EXISTS / IF EXISTS everywhere.
ALTER TABLE analytics.web_events ADD COLUMN IF NOT EXISTS page_title String DEFAULT '';
