package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.entity.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Set<Post> posts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<Subreddit> subreddits = new HashSet<>();

}
