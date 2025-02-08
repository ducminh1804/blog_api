package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    @NotNull
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Column(name = "username")
    @NotNull
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Column(name = "password")
    @NotNull
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column(name = "email")
    @Email
    @NotNull
    @NotBlank(message = "Email cannot be empty")
    private String email;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
}
