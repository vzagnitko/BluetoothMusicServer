CREATE DATABASE music
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE music
    IS 'Bluetooth music library';