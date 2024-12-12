package com.app.bookstore.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    private String author;
    private String description;
    private Double price;
    private String bookLogo;
    private Integer quantity; // It should get reflected by cart
    private Integer cartBookQuantity; // It should set by cart
}
