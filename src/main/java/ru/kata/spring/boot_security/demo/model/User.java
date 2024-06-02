package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "password shold be not empty")
    @Size(min = 2, max = 100, message = "surname should be between 2 and 100")
    private String name;

    @Column(name = "password")
    @NotEmpty(message = "password shold be not empty")
    private String password;

    @Column(name = "surname")
    @NotEmpty(message = "password shold be not empty")
    @Size(min = 2, max = 150, message = "surname should be between 2 and 150")
    private String surname;

    @Column(name = "age")
    @Min(value = 0, message = "age should be more than 0")
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "email shold be not empty")
    @Email
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String password, String surname, int age) {
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.age = age;
    }


    public User(String name, String password, String surname, int age, String email) {
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.age = age;
        this.email = email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Role role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }


    @JsonIgnore
    @Override
    public String getUsername() {
        return name;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }



}
