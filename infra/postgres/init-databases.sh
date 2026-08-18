#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname postgres <<-EOSQL
    CREATE DATABASE new_joiner_auth;
    CREATE DATABASE onboarding_db;
    CREATE DATABASE content_db;
EOSQL