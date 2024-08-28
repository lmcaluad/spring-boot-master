package com.curso.master.mapper;

import com.curso.master.dto.request.SaveRating;
import com.curso.master.dto.response.GetCompleteRating;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.dto.response.GetUser;
import com.curso.master.persistence.entity.Rating;

import java.time.LocalDateTime;
import java.util.List;

public class RatingMapper {

    public static Rating toEntity (SaveRating saveDto, Long userId) {
        if (saveDto == null) return null;

        Rating rating = new Rating();
        rating.setMovieId(saveDto.movieId());
        rating.setUserId(userId);
        rating.setRating(saveDto.rating());
        rating.setCreatedDate(LocalDateTime.now());
        return rating;
    }

    public static GetCompleteRating toGetCompleteRatingDto (Rating entity) {
        if (entity == null) return null;

        String movieTitle = entity.getMovie() != null ? entity.getMovie().getTitle() : null;
        String username = entity.getUser() != null ? entity.getUser().getUsername() : null;

        return new GetCompleteRating(
                entity.getId(),
                entity.getMovieId(),
                movieTitle,
                username,
                entity.getRating()
        );
    }

    public static GetMovie.GetRating toGetMovieRatingDto (Rating entity) {
        if (entity == null) return null;

        String username = entity.getUser() != null
                ? entity.getUser().getUsername()
                : null;

        return new GetMovie.GetRating(
                entity.getId(),
                entity.getRating(),
                username
        );

    }

    public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(RatingMapper::toGetMovieRatingDto)
                .toList();

    }

    public static GetUser.GetRating toGetUserRatingDto (Rating entity) {
        if (entity == null) return null;

        String title = entity.getMovie() != null
                ? entity.getMovie().getTitle()
                : null;

        return new GetUser.GetRating(
                entity.getId(),
                title,
                entity.getMovieId(),
                entity.getRating()
        );
    }

    public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(RatingMapper::toGetUserRatingDto)
                .toList();

    }

    public static void updateEntity(Rating entity, SaveRating dto, Long userId) {
        if (entity == null || dto == null) return;

        entity.setUserId(userId);
        entity.setRating(dto.rating());
        entity.setMovieId(dto.movieId());
        entity.setCreatedDate(LocalDateTime.now());

    }
}
