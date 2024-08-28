package com.curso.master.service.impl;

import com.curso.master.dto.request.MovieSearchCriteria;
import com.curso.master.dto.request.SaveMovie;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.exception.ObjectNotFoundException;
import com.curso.master.mapper.MovieMapper;
import com.curso.master.persistence.entity.Movie;
import com.curso.master.persistence.repository.MovieCrudRepository;
import com.curso.master.persistence.specification.FindAllMoviesSpecification;
import com.curso.master.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional // Hace que la soperaciones sea hagan todas o no se haga ni una
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable)
    {
        FindAllMoviesSpecification moviesSpecification =
                new FindAllMoviesSpecification(searchCriteria);

        Page<Movie> entities = movieCrudRepository.findAll(moviesSpecification, pageable);
        return entities.map(MovieMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetMovie findOneById(Long id)
    {
        return MovieMapper.toGetDto(
                this.findOneEntityById(id)
        );
    }

    @Transactional(readOnly = true)
    private Movie findOneEntityById(Long id)
    {
        return movieCrudRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("[movie:"+ id +"]"));
    }

    @Override
    public GetMovie createOne(SaveMovie saveDto)
    {
        Movie newMovie = MovieMapper.toEntity(saveDto);

        return MovieMapper.toGetDto(
                movieCrudRepository.save(newMovie));
    }

    @Override
    public GetMovie updateOneById(Long id, SaveMovie saveDto)
    {
        Movie oldMovie = this.findOneEntityById(id);
        MovieMapper.updateEntity(oldMovie, saveDto);

        return MovieMapper.toGetDto(
                movieCrudRepository.save(oldMovie)
        );
    }

    @Override
    public void deleteOnById(Long id)
    {
        Movie movie = this.findOneEntityById(id);
        movieCrudRepository.delete(movie);
    }
}
