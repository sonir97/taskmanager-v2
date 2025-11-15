-- For running using psql in windows/mac/linux

--connect to taskmanagerv2 \c short for connect
\c taskmanagerv2;

CREATE SCHEMA IF NOT EXISTS taskmanager_schema AUTHORIZATION postgres;

CREATE USER taskmanager_admin WITH PASSWORD 'Admin@1234';
CREATE USER taskmanager_app WITH PASSWORD 'App@1234';

GRANT CONNECT ON DATABASE taskmanagerv2 TO taskmanager_admin;
GRANT USAGE, CREATE ON SCHEMA taskmanager_schema TO taskmanager_admin;
ALTER SCHEMA taskmanager_schema OWNER TO taskmanager_admin;

GRANT CONNECT ON DATABASE taskmanagerv2 TO taskmanager_app;
GRANT USAGE ON SCHEMA taskmanager_schema TO taskmanager_app;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA taskmanager_schema TO taskmanager_app;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA taskmanager_schema TO taskmanager_app;

ALTER DEFAULT PRIVILEGES FOR ROLE taskmanager_admin IN SCHEMA taskmanager_schema
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO taskmanager_app;

ALTER DEFAULT PRIVILEGES FOR ROLE taskmanager_admin IN SCHEMA taskmanager_schema
GRANT USAGE, SELECT ON SEQUENCES TO taskmanager_app;

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON DATABASE taskmanagerv2 FROM PUBLIC;
