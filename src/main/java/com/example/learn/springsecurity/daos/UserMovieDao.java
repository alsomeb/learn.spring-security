package com.example.learn.springsecurity.daos;

import com.example.learn.springsecurity.domain.Movie;

import java.util.List;

public interface UserMovieDao {
    List<Movie> findMoviesByUser(int userId);
}
