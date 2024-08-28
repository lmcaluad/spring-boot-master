package com.curso.master.mapper;

import com.curso.master.dto.request.SaveMovie;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.persistence.entity.Movie;

import java.time.LocalDateTime;
import java.util.List;

public class MovieMapper {

    public static GetMovie toGetDto(Movie entity) {
        if (entity == null) return null;

        int totalRatings = entity.getRatings() != null ? entity.getRatings().size() : 0;

        return new GetMovie(
                entity.getId(),
                entity.getTitle(),
                entity.getDirector(),
                entity.getGenre(),
                entity.getReleaseYear(),
                totalRatings
        );
    }

    public static List<GetMovie> toGetDtoList(List<Movie> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(MovieMapper::toGetDto)
                .toList();
    }

    public static Movie toEntity(SaveMovie saveDto) {
        if (saveDto == null) return null;

        Movie newMovie = new Movie();
        newMovie.setTitle(saveDto.title());
        newMovie.setDirector(saveDto.director());
        newMovie.setReleaseYear(saveDto.releaseYear());
        newMovie.setGenre(saveDto.genre());
        newMovie.setCreatedDate(LocalDateTime.now());
        return newMovie;
    }

    public static void updateEntity(Movie oldMovie, SaveMovie saveDto) {
        if (oldMovie == null || saveDto == null) return;

        oldMovie.setGenre(saveDto.genre());
        oldMovie.setReleaseYear(saveDto.releaseYear());
        oldMovie.setTitle(saveDto.title());
        oldMovie.setDirector(saveDto.director());
    }
}
