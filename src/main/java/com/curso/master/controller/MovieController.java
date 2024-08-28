package com.curso.master.controller;

import com.curso.master.dto.request.MovieSearchCriteria;
import com.curso.master.dto.request.SaveMovie;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.exception.ObjectNotFoundException;
import com.curso.master.service.MovieService;
import com.curso.master.service.RatingService;
import com.curso.master.util.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetMovie>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) MovieGenre genre,
            @RequestParam(required = false, value = "min_release_year") Integer minReleaseYear,
            @RequestParam(required = false, value = "max_release_year") Integer maxReleaseYear,
            @RequestParam(required = false, value = "average_rating") Integer minAverageRating,
            Pageable moviePageable)

            //    Reemplazados por los valores de la documentacion de pageable
            //    @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            //    @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            //    @RequestParam(required = false, defaultValue = "id") String sortBy
    {
        //    Reemplazados por los valores de la documentacion de Sort
        //Sort sort = Sort.by(sortBy.trim());
        //Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        MovieSearchCriteria searchCriteria =
                new MovieSearchCriteria(title, genre, minReleaseYear, maxReleaseYear, minAverageRating);

        Page<GetMovie> movies = movieService.findAll(searchCriteria, moviePageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<GetMovie> findOneById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(movieService.findOneById(id));
    }

    @GetMapping("/{id}/ratings")
    @ResponseBody
    public ResponseEntity<Page<GetMovie.GetRating>> findAllRatingForMovieById(
            @PathVariable Long id,
            Pageable pageable)
    {
        return ResponseEntity.ok(ratingService.findAllByMovieId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createOne(
            @Valid @RequestBody SaveMovie saveDto,
            HttpServletRequest request)
    {
        //System.out.println("Fecha: " + saveDto.availabilityEndTime());
        GetMovie newMovie = movieService.createOne(saveDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + newMovie.id());

        return ResponseEntity
                .created(newLocation)
                .body(newMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetMovie> updateOne(
            @PathVariable Long id,
            @Valid @RequestBody SaveMovie saveDto)
    {
        GetMovie oldMovie = movieService.updateOneById(id, saveDto);
        return ResponseEntity.ok(oldMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetMovie> deleteOneById(
            @PathVariable Long id)
    {
        movieService.deleteOnById(id);
        return ResponseEntity.noContent().build();
    }

}
