package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.WishList;

import java.util.List;

public class WishListMapper
{
    public JsonResponseDTO returnList(WishList wishList,String bName)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .data(List.of(wishList))
                .message(bName+" added to the wishlist!!").build();
    }

    public JsonResponseDTO inWishList()
    {
        return JsonResponseDTO.builder()
                .result(true)
                .data(null)
                .message("Book is in wishlist!!").build();
    }

    public JsonResponseDTO notInWishList()
    {
        return JsonResponseDTO.builder()
                .result(false)
                .data(null)
                .message("WishList retrieved!!").build();
    }

    public JsonResponseDTO wishListRemoved(String bookName)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .message(bookName+" removed from the wishlist!!")
                .data(null).build();
    }

    public JsonResponseDTO returnWishList(List<Book> books)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .message("WishList retrieved")
                .data(books).build();
    }
}
