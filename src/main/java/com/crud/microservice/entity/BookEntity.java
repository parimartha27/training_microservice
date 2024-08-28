package com.crud.microservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Title cannot be null, empty or blank")
    @Column(name = "TITLE")
    private String title;

    @NotBlank(message = "Author cannot be null, empty or blank")
    @Column(name = "AUTHOR")
    private String author;

    @Min(value = 1, message = "Release date must be greater than 0")
    @Column(name = "RELEASE_DATE")
    private Integer releaseDate;
}
