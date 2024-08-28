package com.curso.master.dto.request;

import com.curso.master.util.MovieGenre;

public record MovieSearchCriteria(
        String title,
        MovieGenre genre,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating
) {
}
