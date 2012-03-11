# --- First database schema
 
# --- !Ups
 
CREATE TABLE url (
  id             INT AUTO_INCREMENT PRIMARY KEY,
  url            CLOB NOT NULL,
  counter        INT NOT NULL DEFAULT 0
);
 
# --- !Downs
 
DROP TABLE IF EXISTS url;
