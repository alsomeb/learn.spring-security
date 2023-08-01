-- Connect to the moviedb database
\connect moviedb;

-- Create the Movies table
CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    release_year DATE NOT NULL
);

-- Create the Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Create the user_movie association table for Many-to-Many relationship
CREATE TABLE user_movie (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE, -- delete a user or movie it removes from assoc. table
    movie_id INTEGER REFERENCES movies (id) ON DELETE CASCADE
);