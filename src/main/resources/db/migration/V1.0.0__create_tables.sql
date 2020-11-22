-- Users
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    description VARCHAR (100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tags
CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    code VARCHAR (100),
    name VARCHAR (100),
    description VARCHAR (100),
    user_id SERIAL REFERENCES users,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE FUNCTION set_update_time() RETURNS OPAQUE AS $$
    BEGIN
        new.updated_at := current_timestamp;
        new.created_at := current_timestamp;
        return new;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tags_trigger BEFORE UPDATE ON tags
    FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TRIGGER users_trigger BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE PROCEDURE set_update_time();
