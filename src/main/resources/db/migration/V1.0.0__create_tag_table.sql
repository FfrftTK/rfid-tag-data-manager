CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    tagId VARCHAR (100) NOT NULL,
    tagName VARCHAR (100),
    description VARCHAR (100)
);