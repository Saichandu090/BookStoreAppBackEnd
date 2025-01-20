package com.app.bookstore.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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
