package com.example.learn.springsecurity.dto;

import com.example.learn.springsecurity.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO {
    private int id;
    private String name;
    private LocalDate releaseYear;

    public static MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .releaseYear(movie.getReleaseYear())
                .build();
    }

    public static Movie toEntity(MovieDTO movieDTO) {
        return Movie.builder()
                .id(movieDTO.getId())
                .name(movieDTO.getName())
                .releaseYear(movieDTO.getReleaseYear())
                .build();
    }
}
