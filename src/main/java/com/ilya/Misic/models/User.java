package com.ilya.Misic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ilya.Misic.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_system")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    private String numberPhone;
    @Column(name = "name")
    private String name;
    @Column(name = "password", length = 1000)
    private String password;
    @Column(name = "active")
    private boolean active;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Product> announcements = new HashSet<>();

    public User() {}

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    public boolean isUser() {
        return roles.contains(Role.ROLE_USER);
    }

    public boolean isModer() {
        return roles.contains(Role.ROLE_MANAGER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
