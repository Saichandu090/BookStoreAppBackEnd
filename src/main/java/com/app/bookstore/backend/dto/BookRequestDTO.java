package com.app.bookstore.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO
{
    @NotNull
    private String name;
    @NotNull
    private String author;
    @NotNull
    private String description;
    private Double price;
    @Min(value = 16)
    private Integer quantity;

    private String bookLogo;
}
