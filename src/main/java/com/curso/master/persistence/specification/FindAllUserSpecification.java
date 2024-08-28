package com.curso.master.persistence.specification;

import com.curso.master.dto.request.UserSearchCriteria;
import com.curso.master.persistence.entity.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllUserSpecification implements Specification<User> {

    private final UserSearchCriteria searchCriteria;

    public FindAllUserSpecification(UserSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(this.searchCriteria.username()))
        {
            Predicate titleLike =
                    criteriaBuilder.like(root.get("username"), "%" + this.searchCriteria.username() + "%");
            predicates.add(titleLike);
        }

        if (this.searchCriteria.name() != null)
        {
            Predicate genreEqual =
                    criteriaBuilder.like(root.get("name"), "%" + this.searchCriteria.name() + "%");
            predicates.add(genreEqual);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
