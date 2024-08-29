package com.crud.microservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersJwtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be null, empty or blank")
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be null, empty or blank")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

}