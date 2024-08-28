package com.curso.master.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public record SaveRating(
        @Positive(message = "{generic.positive}")
        @JsonProperty(value = "movie_id")
        Long movieId,

        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveUser.username.pattern}")
        String username,

        @Min(value = 0, message = "{generic.min}")
        @Max(value = 5, message = "{generic.max}")
        int rating
)  implements Serializable {
}
