package com.curso.master.persistence.repository;

import com.curso.master.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>,
        JpaSpecificationExecutor<Movie> {
}
