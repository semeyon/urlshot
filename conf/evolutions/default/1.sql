# --- First database schema
 
# --- !Ups
 
CREATE TABLE urls (
  id                       INT PRIMARY KEY,
  url                      CLOB NOT NULL,
  conter                   INT NOT NULL DEFAULT 0
);
 
# --- !Downs
 
DROP TABLE IF EXISTS urls;
