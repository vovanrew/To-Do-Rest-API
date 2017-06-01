CREATE TABLE tasks (
  id      SERIAL PRIMARY KEY,
  title   CHAR (50) NOT NULL,
  text    CHAR (200),
  done    BOOLEAN,
  user_id INTEGER REFERENCES users (id)
);