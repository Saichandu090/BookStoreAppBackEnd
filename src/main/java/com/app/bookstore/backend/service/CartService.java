package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Cart;

public interface CartService
{
    Cart addToCart(String email, CartRequestDTO requestDTO);

    JsonResponseDTO getUserCart(String email);

    JsonResponseDTO removeFromCart(String email, Long bookId);

    JsonResponseDTO clearCart(String email);
}
