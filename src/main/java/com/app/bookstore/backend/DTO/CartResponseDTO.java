package com.app.bookstore.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO
{
    private Long id;
    private List<BookResponseDTO> booksList;
    private Long quantity;
    private Long totalPrice;
}
