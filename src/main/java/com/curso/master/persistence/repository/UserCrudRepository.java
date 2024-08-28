package com.curso.master.persistence.repository;

import com.curso.master.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional // Cuando la modificación en la base de datos no esta en JpaRepository
    void deleteByUsername(String username);
}
