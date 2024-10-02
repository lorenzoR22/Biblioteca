package com.tuLibro.tuLibro.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class,cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns =@JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<RoleEntity> role;

    public UserEntity(String username, String password, String mail, Set<RoleEntity> role) {
        this.username = username;
        this.password = password;
        this.email =mail;
        this.role = role;
    }
}
