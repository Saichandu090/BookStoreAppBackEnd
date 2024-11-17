package com.app.bookstore.backend.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO
{
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String author;
    @NotNull
    private String description;
    private Double price;
    @Min(value = 16)
    private Integer quantity;
}
