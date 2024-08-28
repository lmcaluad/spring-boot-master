package com.curso.master.service;

import com.curso.master.dto.request.SaveUser;
import com.curso.master.dto.request.UserSearchCriteria;
import com.curso.master.dto.response.GetUser;
import com.curso.master.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<GetUser> findAll(UserSearchCriteria searchCriteria, Pageable pageable);

    GetUser findOneByUsername(String username);

    User findOneEntityByUsername(String username);

    GetUser saveOne(SaveUser saveDto);

    GetUser updateOneByUsername(String username, SaveUser saveDto);

    void deleteOneByUsername(String username);

}
