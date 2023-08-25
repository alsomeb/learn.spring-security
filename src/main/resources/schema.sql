-- FOR H2 DB, Testing Purposes

-- Drop Tables If Exists
DROP TABLE IF EXISTS "MOVIES";
DROP TABLE IF EXISTS "APP_USERS";
DROP TABLE IF EXISTS "user_movie";

-- Create the Movies table
CREATE TABLE "MOVIES" (
      id SERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      release_year DATE NOT NULL
);

-- Create the Users table
CREATE TABLE "APP_USERS" (
     id SERIAL PRIMARY KEY,
     username VARCHAR(255) NOT NULL,
     email VARCHAR(255) NOT NULL UNIQUE -- only 1 email / User
);

-- Create the user_movie association table for Many-to-Many relationship
CREATE TABLE "USER_MOVIE" (
      id SERIAL PRIMARY KEY,
      user_id INTEGER REFERENCES "APP_USERS" (id) ON DELETE CASCADE, -- delete a user or movie it removes from assoc. table
      movie_id INTEGER REFERENCES "MOVIES" (id) ON DELETE CASCADE
);


-- SPRING SECURITY STUFF--
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR_IGNORECASE(50) NOT NULL,
    password VARCHAR_IGNORECASE(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    authority VARCHAR_IGNORECASE(50) NOT NULL
);
