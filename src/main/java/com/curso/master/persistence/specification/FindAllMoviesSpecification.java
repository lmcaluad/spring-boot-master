package com.curso.master.persistence.specification;

import com.curso.master.dto.request.MovieSearchCriteria;
import com.curso.master.persistence.entity.Movie;
import com.curso.master.persistence.entity.Rating;
import com.curso.master.util.MovieGenre;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {

//    private String title;
//    private MovieGenre genre;
//    private Integer minReleaseYear;

    private final MovieSearchCriteria searchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // root = from Movie
        // query = criterios de la consulta en si misma
        // criteriaBuilder = fabria que permite construir predicados y expresiones

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(this.searchCriteria.title()))
        {
            Predicate titleLike =
                    criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
            predicates.add(titleLike);
        }

        if (this.searchCriteria.genre() != null)
        {
            Predicate genreEqual =
                    criteriaBuilder.equal(root.get("genre"), this.searchCriteria.genre());
            predicates.add(genreEqual);
        }

        if (this.searchCriteria.minReleaseYear() != null
                && this.searchCriteria.minReleaseYear() > 0)
        {
            Predicate releaseYearGreaterThanEqual =
                    criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.minReleaseYear());
            predicates.add(releaseYearGreaterThanEqual);
        }

        if (this.searchCriteria.maxReleaseYear() != null
                && this.searchCriteria.maxReleaseYear() > 0)
        {
            Predicate releaseYearLessThanEqual =
                    criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.searchCriteria.maxReleaseYear());
            predicates.add(releaseYearLessThanEqual);
        }

        if (this.searchCriteria.minAverageRating() != null
                && this.searchCriteria.minAverageRating() >= 0)
        {
            Subquery<Double> avgRatingSubquery = getAverageRatingSubQuery(root, query, criteriaBuilder);

            Predicate condition = criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicates.add(condition);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private static Subquery<Double> getAverageRatingSubQuery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Double> avgRatingSubquery = query.subquery(Double.class);
        Root<Rating> subqueryRoot = avgRatingSubquery.from(Rating.class);
        avgRatingSubquery.select(criteriaBuilder.avg(subqueryRoot.get("rating")))
                .where(criteriaBuilder.equal(subqueryRoot.get("movie"), root));
        return avgRatingSubquery;
    }
}
