package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO
{
    private Long cartId;
    private Long userId;
    private Long bookId;
    private String bookName;
    private String bookLogo;
    private int quantity;
    private double totalPrice;
}
