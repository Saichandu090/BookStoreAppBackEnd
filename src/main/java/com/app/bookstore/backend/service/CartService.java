package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;

public interface CartService
{
    JsonResponseDTO addToCart(String email, CartRequestDTO requestDTO);

    JsonResponseDTO getUserCarts(String email);

    JsonResponseDTO removeFromCart(String email, Long cartId);

    JsonResponseDTO clearCart(String email);

    JsonResponseDTO getAllCarts();
}
