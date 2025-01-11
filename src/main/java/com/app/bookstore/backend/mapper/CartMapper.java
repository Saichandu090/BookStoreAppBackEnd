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
    //Converting User,Book and quantity to Cart
    public Cart addToCart(User user, Book book, int quantity)
    {
        Cart cart=new Cart();
        //cart.setBooks(List.of(book));
        //cart.setUser(user);
        cart.setQuantity(quantity);
        cart.setTotalPrice(cart.getQuantity()*book.getPrice());
        return cart;
    }

    public JsonResponseDTO updateCartQuantity(Cart cart)
    {
        cart.setQuantity(cart.getQuantity()+1);
        cart.setTotalPrice(cart.getTotalPrice()+cart.getBook().getPrice());
        return saveCart(cart);
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

    public JsonResponseDTO removeCart(Cart cart)
    {
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Cart is Empty");
        jsonResponseDTO.setData(null);
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }

    public JsonResponseDTO returnCart(Cart cart)
    {

        //Converting Cart to CartResponseDTO
        CartResponseDTO dto=new CartResponseDTO();
        dto.setUserId(cart.getUserId());
        dto.setCartId(cart.getCartId());
        dto.setBookId(cart.getBook().getBookId());
        dto.setBookLogo(cart.getBook().getBookLogo());
        dto.setQuantity(cart.getQuantity());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setBookName(cart.getBook().getBookName());

        List<CartResponseDTO> list=new ArrayList<>();
        list.add(dto);

        //Converting CartResponseDTO to JsonResponseDTO
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Cart returned");
        jsonResponseDTO.setData(list);
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

    public JsonResponseDTO returnCartList(List<Cart> carts)
    {
       List<CartResponseDTO> responseDTOS=carts.stream().map(cart -> new CartResponseDTO(cart.getCartId(),cart.getUserId(),cart.getBook().getBookId(),cart.getBook().getBookName(),cart.getBook().getBookLogo(),cart.getQuantity(), cart.getTotalPrice())).toList();
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Carts retrieved successfully!!");
        jsonResponseDTO.setData(responseDTOS);
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }

    public JsonResponseDTO cartRemoved(String message)
    {
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage(message);
        jsonResponseDTO.setData(null);
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }
}
