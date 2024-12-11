package com.app.bookstore.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO
{
    private Long id;
    private String name;
    private String author;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer cartBookQuantity;
    private String bookLogo;
}
