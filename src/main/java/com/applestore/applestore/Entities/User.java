package com.applestore.applestore.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(columnDefinition = "nvarchar(50)" ,nullable = false)
    private String f_name;

    @Column(columnDefinition = "nvarchar(50)")
    private String l_name;

    @Column(name="reset_password_token",columnDefinition = "nvarchar(50)")
    private String resetPasswordToken;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_provider")
    private AuthenticationProvider authProvider;

    @Column(nullable = false)
    private String gmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Collection<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", resetPasswordToken='" + resetPasswordToken + '\'' +
                ", authProvider=" + authProvider +
                ", gmail='" + gmail + '\'' +
                ", roles=" + roles +
                '}';
    }
}