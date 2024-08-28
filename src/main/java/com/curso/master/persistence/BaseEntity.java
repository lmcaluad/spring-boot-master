package com.curso.master.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @CreatedDate
    //@JsonProperty(value = "create-at", access = JsonProperty.Access.READ_ONLY)
    //@JsonFormat(pattern = "yyyy/MM/dd")
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdDate;

    /*@LastModifiedDate
    //@JsonProperty(value = "modified-at", access = JsonProperty.Access.READ_ONLY)
    //@JsonFormat(pattern = "yyyy/MM/dd")
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(insertable = false)
    private Integer lastModifiedBy;*/

}
