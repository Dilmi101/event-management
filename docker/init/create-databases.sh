#!/bin/bash
set -e

CONF_FILE="/docker-entrypoint-initdb.d/databases.conf"

if [ ! -f "$CONF_FILE" ]; then
  echo "No databases.conf found, skipping database creation."
  exit 0
fi

while IFS='|' read -r dbname dbuser dbpass; do
  [ -z "$dbname" ] && continue

  echo "Creating user: $dbuser"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
    -c "CREATE USER \"$dbuser\" WITH PASSWORD '$dbpass';"

  echo "Creating database: $dbname"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
    -c "CREATE DATABASE \"$dbname\" OWNER \"$dbuser\";"

  echo "Granting privileges on $dbname to $dbuser"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$dbname" \
    -c "GRANT ALL PRIVILEGES ON DATABASE \"$dbname\" TO \"$dbuser\";"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$dbname" \
    -c "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO \"$dbuser\";"
done < "$CONF_FILE"
