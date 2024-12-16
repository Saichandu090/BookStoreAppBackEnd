package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.BookRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.WishListDTO;
import com.app.bookstore.backend.model.Book;

public interface WishListService
{
    JsonResponseDTO addToWishList(String email, WishListDTO wishListDTO);

    JsonResponseDTO removeFromWishList(String email,Long bookId);

    JsonResponseDTO getAllWishListItems(String email);

    JsonResponseDTO isInWishList(String email, Long bookId);
}
