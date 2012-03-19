# --- First database schema
 
# --- !Ups
 
CREATE TABLE url (
    id             SERIAL,
    url            TEXT NOT NULL,
    counter        INT NOT NULL DEFAULT 0,
    PRIMARY KEY(id)
);
 
# --- !Downs
 
DROP TABLE IF EXISTS url;
