package com.curso.master.service;

import com.curso.master.dto.request.SaveRating;
import com.curso.master.dto.response.GetCompleteRating;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.dto.response.GetUser;
import com.curso.master.persistence.entity.Movie;
import com.curso.master.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {

    Page<GetCompleteRating> findAll(Pageable pageable);

    Page<GetMovie.GetRating> findAllByMovieId(Long movieId, Pageable pageable);

    Page<GetUser.GetRating> findAllByUsername(String name, Pageable pageable);

    GetCompleteRating findOneById(Long id);

    Rating findOneEntityById(Long id);

    GetCompleteRating createOne(SaveRating saveDto);

    GetCompleteRating updateOneById(Long id, SaveRating saveDto);

    void deleteOnById(Long id);
}
