-- Connect to the moviedb database
\connect moviedb;

-- Drop Tables If Exists
DROP TABLE IF EXISTS 'movies';
DROP TABLE IF EXISTS 'app_users';
DROP TABLE IF EXISTS 'user_movie';

-- Create the Movies table
CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- can use TEXT instead but lets keep varchar for now
    release_year DATE NOT NULL
);

-- Create the Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE -- only 1 email / User
);

-- Create the user_movie association table for Many-to-Many relationship
CREATE TABLE user_movie (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE, -- delete a user or movie it removes from assoc. table
    movie_id INTEGER REFERENCES movies (id) ON DELETE CASCADE
);