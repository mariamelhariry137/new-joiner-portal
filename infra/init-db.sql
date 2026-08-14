-- Create databases for each service
CREATE DATABASE IF NOT EXIST identity_db;
CREATE DATABASE IF NOT EXIST onboarding_db;
CREATE DATABASE IF NOT EXIST content_db;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE identity_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE onboarding_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE content_db TO postgres;