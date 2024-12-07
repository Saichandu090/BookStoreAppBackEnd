package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.CartMapper;
import com.app.bookstore.backend.mapper.UserMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.CartRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService
{
   private CartRepository cartRepository;
   private BookRepository bookRepository;
   private UserRepository userRepository;

   private CartMapper cartMapper;

    @Override
    public Cart addToCart(String email, CartRequestDTO requestDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            // Create a new cart for the user
            cart = new Cart();
            cart.setUser(user);
            cart.setBooks(new ArrayList<>());
            cart.setQuantity(1);
            cart.setTotalPrice(book.getPrice());
            cart.getBooks().add(book); // Add the book to the cart
        } else {
            // Add book to the cart if not already present
            if (!cart.getBooks().contains(book)) {
                cart.getBooks().add(book);
            }
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + book.getPrice());
        }

        // Update the relationship on the book side
        if (book.getCarts() == null) {
            book.setCarts(new ArrayList<>());
        }
        if (!book.getCarts().contains(cart)) {
            book.getCarts().add(cart);
        }

        // Save both entities
        bookRepository.save(book); // Save book if necessary
        return cartRepository.save(cart); // Save cart with updated books
    }


    @Override
    public JsonResponseDTO getUserCart(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not Found"));
        Cart cart=user.getCart();
        if(cart==null)
            return cartMapper.cartEmpty();
        return cartMapper.returnCart(cart);
    }

    @Override
    public JsonResponseDTO removeFromCart(String email, Long bookId) {
        return null;
    }

    @Override
    public JsonResponseDTO clearCart(String email) {
        return null;
    }
}
