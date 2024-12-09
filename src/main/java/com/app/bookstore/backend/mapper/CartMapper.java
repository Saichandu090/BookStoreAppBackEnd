package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.BookResponseDTO;
import com.app.bookstore.backend.DTO.CartResponseDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
        List<BookResponseDTO> responseDTOS=cart.getBooks().stream().map(book->new BookResponseDTO(book.getBookId(),book.getBookName(),book.getAuthor(),book.getDescription(),book.getPrice(),book.getQuantity(), book.getCartBookQuantity())).toList();
        //Converting Cart to CartResponseDTO
        CartResponseDTO dto=new CartResponseDTO();
        dto.setBooksList(responseDTOS);
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

    public JsonResponseDTO returnCartList(List<Cart> carts)
    {
        JsonResponseDTO jsonResponseDTO=new JsonResponseDTO();
        jsonResponseDTO.setMessage("Cart retrieved successfully!!");
        jsonResponseDTO.setData(carts);
        jsonResponseDTO.setResult(true);
        return jsonResponseDTO;
    }
}
