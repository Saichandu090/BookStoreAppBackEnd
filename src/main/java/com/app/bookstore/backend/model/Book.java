package com.app.bookstore.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book
{
    @Id
    private Long id;
    private String name;
    private String author;
    private String description;
    private Double price;
    private Integer quantity;
}
