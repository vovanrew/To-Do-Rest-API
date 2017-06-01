CREATE TABLE users (
  id      SERIAL PRIMARY KEY,
  name    CHAR (50) NOT NULL,
  email   CHAR (50) NOT NULL,
  passwd  CHAR (100) NOT NULL
);