package com.curso.master.service.impl;

import com.curso.master.dto.request.SaveRating;
import com.curso.master.dto.response.GetCompleteRating;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.dto.response.GetUser;
import com.curso.master.exception.DuplicateRatingException;
import com.curso.master.exception.ObjectNotFoundException;
import com.curso.master.mapper.RatingMapper;
import com.curso.master.persistence.entity.Rating;
import com.curso.master.persistence.entity.User;
import com.curso.master.persistence.repository.RatingCrudRepository;
import com.curso.master.service.RatingService;
import com.curso.master.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Autowired
    private UserService userService;

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<GetCompleteRating> findAll(Pageable pageable) {
        return ratingCrudRepository.findAll(pageable)
                .map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    public Page<GetMovie.GetRating> findAllByMovieId(Long movieId, Pageable pageable) {
        return ratingCrudRepository.findByMovieId(movieId, pageable)
                .map(RatingMapper::toGetMovieRatingDto);
    }

    @Override
    public Page<GetUser.GetRating> findAllByUsername(String name, Pageable pageable) {
        return ratingCrudRepository.findByUserUsername(name, pageable)
                .map(RatingMapper::toGetUserRatingDto);
    }

    @Override
    public GetCompleteRating findOneById(Long id) {
        Rating rating = this.findOneEntityById(id);
        return RatingMapper.toGetCompleteRatingDto(rating);
    }

    @Override
    public Rating findOneEntityById(Long id) {
        return ratingCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[rating:"+ id +"]"));
    }

    @Override
    public GetCompleteRating createOne(SaveRating saveDto) {

        boolean ratingExists = ratingCrudRepository.existsByMovieIdAndUserUsername(saveDto.movieId(), saveDto.username());

        if (ratingExists)
            throw new DuplicateRatingException(saveDto.username(), saveDto.movieId());

        User userEntity = userService.findOneEntityByUsername(saveDto.username());

        Rating entity = RatingMapper.toEntity(saveDto, userEntity.getId());
        Rating entitySaved = ratingCrudRepository.save(entity);
        entityManager.detach(entitySaved);

        return ratingCrudRepository.findById(entitySaved.getId())
                .map(RatingMapper::toGetCompleteRatingDto)
                .get();
    }

    @Override
    public GetCompleteRating updateOneById(Long id, SaveRating saveDto) {
        Rating oldRating = this.findOneEntityById(id);
        User userEntity = userService.findOneEntityByUsername(saveDto.username());
        RatingMapper.updateEntity(oldRating, saveDto, userEntity.getId());
        return RatingMapper.toGetCompleteRatingDto(
                ratingCrudRepository.save(oldRating)
        );
    }

    @Override
    public void deleteOnById(Long id) {

        // mas eficiente porque trae sólo una columna
        if(ratingCrudRepository.existsById(id)) {
            ratingCrudRepository.deleteById(id);
            return;
        }

        throw new ObjectNotFoundException("[rating:"+ id +"]");
    }
}
