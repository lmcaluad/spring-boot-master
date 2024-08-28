package com.curso.master.mapper;


import com.curso.master.dto.request.SaveUser;
import com.curso.master.dto.response.GetUser;
import com.curso.master.persistence.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserMapper {

    public static GetUser toGetDto(User entity) {
        if (entity == null) return null;

        int totalRatings = entity.getRatings() != null ? entity.getRatings().size() : 0;

        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                totalRatings
                //RatingMapper.toGetUserRatingDtoList(entity.getRatings())
        );
    }

    public static List<GetUser> toGetDtoList(List<User> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(UserMapper::toGetDto)
                .toList();
    }

    public static User toEntity(SaveUser saveDto) {
        if (saveDto == null) return null;

        User newUser = new User();
        newUser.setUsername(saveDto.username());
        newUser.setName(saveDto.name());
        newUser.setPassword(saveDto.password());
        newUser.setCreatedDate(LocalDateTime.now());
        return newUser;
    }

    public static void updateEntity(User oldUser, SaveUser saveDto) {
        if (oldUser == null || saveDto == null) return;

        oldUser.setName(saveDto.name());
        oldUser.setPassword(saveDto.password());
    }
}
