package com.curso.master.dto.response;

import com.curso.master.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record GetMovie(
        long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year") int releaseYear,
        @JsonProperty(value = "total_ratings") int totalRatings

) implements Serializable {

    public record GetRating (
            long id,
            int rating,
            String username

    ) implements Serializable {

    }
}
