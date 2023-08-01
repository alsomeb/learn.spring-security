-- Connect to the moviedb database
\connect moviedb;

-- Seed users, thanks CHAT GPT
INSERT INTO users (username, email)
VALUES
    ('JohnDoe', 'john.doe@example.com'),
    ('JaneSmith', 'jane.smith@example.com'),
    ('MikeJohnson', 'mike.johnson@example.com'),
    ('EmilyBrown', 'emily.brown@example.com'),
    ('AlexWilson', 'alex.wilson@example.com'),
    ('OliviaLee', 'olivia.lee@example.com'),
    ('DanielTaylor', 'daniel.taylor@example.com'),
    ('SophiaMartinez', 'sophia.martinez@example.com'),
    ('WilliamAnderson', 'william.anderson@example.com'),
    ('IsabellaThomas', 'isabella.thomas@example.com'),
    ('Alsomeb', 'alex@gmail.com');

-- Seed movies
INSERT INTO movies (name, release_year)
VALUES
    ('The Secret Garden', '2020-08-07'),
    ('Midnight in Paris', '2011-05-20'),
    ('Eternal Sunshine of the Spotless Mind', '2004-03-19'),
    ('Inception', '2010-07-16'),
    ('La La Land', '2016-12-25'),
    ('The Grand Budapest Hotel', '2014-03-07'),
    ('Interstellar', '2014-11-07'),
    ('The Shawshank Redemption', '1994-09-23'),
    ('The Godfather', '1972-03-24'),
    ('Pulp Fiction', '1994-10-14'),
    ('The Dark Knight', '2008-07-18'),
    ('Avatar', '2009-12-18'),
    ('The Lord of the Rings: The Fellowship of the Ring', '2001-12-19'),
    ('Forrest Gump', '1994-07-06'),
    ('Titanic', '1997-12-19'),
    ('Star Wars: Episode IV - A New Hope', '1977-05-25'),
    ('Jurassic Park', '1993-06-11'),
    ('The Lion King', '1994-06-15'),
    ('The Matrix', '1999-03-31'),
    ('Gladiator', '2000-05-05');

-- Seed user_movie association table (user's watchlist)
INSERT INTO user_movie (user_id, movie_id)
VALUES
    (1, 1), (1, 2),
    (2, 3), (2, 4),
    (3, 5), (3, 6),
    (4, 7), (4, 8),
    (5, 9), (5, 10),
    (6, 11), (6, 12),
    (7, 13), (7, 14),
    (8, 15), (8, 16),
    (9, 17), (9, 18),
    (10, 19), (10, 20);
