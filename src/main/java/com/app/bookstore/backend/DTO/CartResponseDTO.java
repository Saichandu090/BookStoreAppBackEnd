package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Book;
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
    private List<Book> booksList;
    private int quantity;
    private double totalPrice;
}
