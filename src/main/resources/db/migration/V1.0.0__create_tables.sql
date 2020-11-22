CREATE TABLE IF NOT EXISTS tags (
    id VARCHAR (100) PRIMARY KEY,
    name VARCHAR (100),
    description VARCHAR (100)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    description VARCHAR (100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE FUNCTION set_update_time() RETURNS OPAQUE AS $$
    BEGIN
        new.updated_at := current_timestamp;
        return new;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_trigger BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE PROCEDURE set_update_time();
