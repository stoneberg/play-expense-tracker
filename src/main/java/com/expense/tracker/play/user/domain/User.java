package com.expense.tracker.play.user.domain;

import com.expense.tracker.play.common.audit.AuditorBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "et_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends AuditorBaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(length = 20, nullable = false)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(length = 120, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "et_user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_ROLE")),
            foreignKey = @ForeignKey(name = "FK_USER"),
            inverseForeignKey = @ForeignKey(name = "FK_ROLE")
    )
    private Set<Role> roles = new HashSet<>();

    @Builder
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // set user roles
    public void addUserRoles(List<Role> userRoles) {
        roles.addAll(userRoles);
    }

}
