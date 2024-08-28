package com.curso.master.dto.request;

import com.curso.master.util.MovieGenre;

public record UserSearchCriteria(
        String username,
        String name
) {
}
