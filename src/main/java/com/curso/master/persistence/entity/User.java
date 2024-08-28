package com.curso.master.persistence.entity;

import com.curso.master.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    private String name;

    @Column(nullable = false)
    private String password;

    //@JsonManagedReference("user-to-ratings") // Sólo se usa cuando la relación es bidireccional
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Rating> ratings;
}
