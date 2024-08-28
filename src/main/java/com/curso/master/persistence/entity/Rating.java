package com.curso.master.persistence.entity;

import com.curso.master.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rating extends BaseEntity {

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    //@JsonBackReference("movie-to-ratings")
    //@JsonIgnore
    //@Setter(AccessLevel.PROTECTED)
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    //@JsonBackReference("user-to-ratings")
    //@JsonIgnore
    //@Setter(AccessLevel.PROTECTED)
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Check(constraints = "rating >= 0 and rating <= 5")
    @Column(nullable = false)
    private int rating;
}
