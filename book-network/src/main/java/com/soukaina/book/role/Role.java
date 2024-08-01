package com.soukaina.book.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soukaina.book.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // to ignore the serialization fo this one, so we won't have any circular dependencies
    private List<User> users;

    @CreatedDate // we add this in order to let it automatically audited/tracked
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false) // when we create a new record, we don't want to initialize the value of this attribute
    private LocalDateTime lastModifiedDate;
}
