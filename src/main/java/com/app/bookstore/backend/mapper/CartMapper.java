package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.CartResponseDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class CartMapper
{
    public JsonResponseDTO updateCartQuantity(Cart cart)
    {
        cart.setQuantity(cart.getQuantity()+1);
        cart.setTotalPrice(cart.getTotalPrice()+cart.getBook().getPrice());
        return saveCart(cart);
    }

    //Converting Cart to JsonResponseDTO
    public JsonResponseDTO saveCart(Cart cart)
    {
        return JsonResponseDTO.builder()
                .message("Book added to cart")
                .result(true)
                .data(List.of(cart)).build();
    }

    public JsonResponseDTO returnCart(Cart cart)
    {
        CartResponseDTO dto= CartResponseDTO.builder()
                .userId(cart.getCartId())
                .cartId(cart.getCartId())
                .bookId(cart.getBook().getBookId())
                .bookLogo(cart.getBook().getBookLogo())
                .quantity(cart.getQuantity())
                .totalPrice(cart.getTotalPrice())
                .bookName(cart.getBook().getBookName()).build();
        List<CartResponseDTO> list=new ArrayList<>();
        list.add(dto);

        return JsonResponseDTO.builder()
                .data(list)
                .result(true)
                .message("Cart returned").build();
    }

    public JsonResponseDTO returnCartList(List<Cart> carts)
    {
       List<CartResponseDTO> responseDTOS=
               carts.stream().map(cart -> new CartResponseDTO(cart.getCartId(),cart.getUserId(),cart.getBook().getBookId(),cart.getBook().getBookName(),cart.getBook().getBookLogo(),cart.getQuantity(), cart.getTotalPrice())).toList();

       return JsonResponseDTO.builder()
               .message("Carts retrieved successfully!!")
               .data(responseDTOS)
               .result(true).build();
    }

    public JsonResponseDTO cartRemoved(String message)
    {
        return JsonResponseDTO.builder()
                .result(true)
                .message(message)
                .data(null).build();
    }
}
