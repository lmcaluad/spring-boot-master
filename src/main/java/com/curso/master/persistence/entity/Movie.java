package com.curso.master.persistence.entity;

import com.curso.master.persistence.BaseEntity;
import com.curso.master.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    private int releaseYear;

    //@JsonManagedReference("movie-to-ratings")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
    private List<Rating> ratings;
}
