package com.curso.master.service;

import com.curso.master.dto.request.MovieSearchCriteria;
import com.curso.master.dto.request.SaveMovie;
import com.curso.master.dto.response.GetMovie;
import com.curso.master.util.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    Page<GetMovie> findAll(MovieSearchCriteria searchCriteria, Pageable pageable);

    GetMovie findOneById(Long id);

    GetMovie createOne(SaveMovie saveDto);

    GetMovie updateOneById(Long id, SaveMovie saveDto);

    void deleteOnById(Long id);

}
