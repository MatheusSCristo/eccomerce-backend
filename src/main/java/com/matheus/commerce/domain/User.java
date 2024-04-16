package com.matheus.commerce.domain;

import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "tb_user")
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private int age;
    private String email;
    @Column(name = "verified_email")
    private boolean verifiedEmail;
    private String password;
    private Role role;
    private String cpf;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders")
    private Set<Order> orders = new HashSet<>();


    public User(UserCreateDto userCreateDto, String encryptedPassword) {
        this.name = userCreateDto.name();
        this.lastName = userCreateDto.lastName();
        this.age = userCreateDto.age();
        this.email = userCreateDto.email();
        this.password = encryptedPassword;
        this.role = userCreateDto.role();
        this.cpf = userCreateDto.cpf();
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}
