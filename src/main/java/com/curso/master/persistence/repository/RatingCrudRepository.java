package com.curso.master.persistence.repository;

import com.curso.master.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingCrudRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findByMovieId(Long id, Pageable pageable);

    Page<Rating> findByUserUsername(String username, Pageable pageable);

    boolean existsByMovieIdAndUserUsername(Long movieId, String username);
}
