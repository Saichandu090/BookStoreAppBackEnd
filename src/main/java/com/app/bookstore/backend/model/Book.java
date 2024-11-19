package com.app.bookstore.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToMany(mappedBy = "bookList",fetch = FetchType.LAZY)
    private List<Cart> carts;
}
