package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.CartResponseDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;

import java.util.List;

public class CartMapper
{
    //Converting User,Book and quantity to Cart
    public Cart addToCart(User user, Book book, int quantity)
    {
        Cart cart=new Cart();
        cart.setBooks(List.of(book));
        cart.setUser(user);
        cart.setQuantity(quantity);
        cart.setTotalPrice(cart.getQuantity()*book.getPrice());
        return cart;
    }

    //Converting Cart to JsonResponseDTO
    public JsonResponseDTO saveCart(Cart cart)
    {
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Book added to cart");
        jsonResponseDTO.setData(List.of(cart));
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }

    public JsonResponseDTO updateCart(Cart cart)
    {
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Book removed from cart");
        jsonResponseDTO.setData(List.of(cart));
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }

    public JsonResponseDTO returnCart(Cart cart)
    {
        //Converting Cart to CartResponseDTO
        CartResponseDTO dto=new CartResponseDTO();
        dto.setBooksList(cart.getBooks());
        dto.setQuantity(cart.getQuantity());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setId(cart.getCartId());

        //Converting CartResponseDTO to JsonResponseDTO
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Cart returned");
        jsonResponseDTO.setData(List.of(dto));
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }

    //If Cart is Empty
    public JsonResponseDTO cartEmpty()
    {
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Cart is empty");
        jsonResponseDTO.setData(null);
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }
}
