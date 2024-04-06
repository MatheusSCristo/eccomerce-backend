package com.matheus.commerce.domain;

import com.matheus.commerce.dto.user.UserCreateDto;
import com.matheus.commerce.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "tb_user")
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
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

    public User(UserCreateDto userCreateDto){
        this.name= userCreateDto.name();
        this.lastName= userCreateDto.lastName();
        this.age= userCreateDto.age();
        this.email= userCreateDto.email();
        this.password= userCreateDto.password();
        this.role=userCreateDto.role();
        this.cpf= userCreateDto.cpf();
        this.createdAt=LocalDate.now();
        this.updatedAt=LocalDate.now();
    }



}
