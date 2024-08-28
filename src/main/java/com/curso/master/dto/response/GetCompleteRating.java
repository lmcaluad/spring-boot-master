package com.curso.master.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetCompleteRating(
        @JsonProperty(value = "rating_id") long ratingId,
        @JsonProperty(value = "movie_id") long movieId,
        @JsonProperty(value = "movie_title") String movieTitle,
        String username,
        int rating
) implements Serializable {
}
