package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.WishListDTO;

public interface WishListService
{
    JsonResponseDTO addToWishList(String email, WishListDTO wishListDTO);

    JsonResponseDTO removeFromWishList(String email,Long bookId);

    JsonResponseDTO getAllWishListItems(String email);

    JsonResponseDTO isInWishList(String email, Long bookId);
}
