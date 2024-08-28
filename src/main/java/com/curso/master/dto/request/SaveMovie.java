package com.curso.master.dto.request;

import com.curso.master.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public record SaveMovie(
        @Size(min = 4, max = 255, message = "{generic.size}")
        @NotBlank(message = "{generic.notblank}")
        String title,

        @Size(min = 4, max = 255, message = "{generic.size}")
        @NotBlank(message = "{generic.notblank}")
        String director,

        MovieGenre genre,

        @Min(value = 1900, message = "{generic.min}")
        @JsonProperty(value = "release_year")
        int releaseYear

        /*@JsonProperty("availability_end_time")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Future
        LocalDateTime availabilityEndTime*/

) implements Serializable {
}
