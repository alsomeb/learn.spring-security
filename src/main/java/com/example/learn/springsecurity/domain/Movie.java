package com.example.learn.springsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    private int id;
    private String name;
    private LocalDate releaseYear;
}
