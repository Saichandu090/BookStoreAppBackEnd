package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;

public interface WishListService
{
    JsonResponseDTO addToWishList(String email, Long bookId);

    JsonResponseDTO removeFromWishList(String email,Long bookId);

    JsonResponseDTO getAllWishListItems(String email);

}
