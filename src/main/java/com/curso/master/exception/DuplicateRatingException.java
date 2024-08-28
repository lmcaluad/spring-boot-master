package com.curso.master.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateRatingException extends RuntimeException {

    private final String username;
    private final Long movieId;

    public DuplicateRatingException(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
    }

    @Override
    public String getMessage() {
        return String.format("Rating already submitted for movie with ID %d by user %s. Only one rating per per movie is allowed", this.getMovieId(), this.getUsername());
    }
}
