package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.exception.CartNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.CartMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.CartRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CartServiceImpl implements CartService
{
   private CartRepository cartRepository;
   private BookRepository bookRepository;
   private UserRepository userRepository;
   private CartMapper cartMapper;

    @Override
    public JsonResponseDTO addToCart(String email, CartRequestDTO requestDTO)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Cart cart=new Cart();
        cart.setUserId(user.getUserId());
        cart.setBookId(book.getBookId());
        cart.setQuantity(requestDTO.getQuantity());
        cart.setTotalPrice(book.getPrice() * cart.getQuantity());

        book.setQuantity(book.getQuantity()- cart.getQuantity());
        book.setCartBookQuantity(book.getCartBookQuantity()+ cart.getQuantity());

        return cartMapper.saveCart(cartRepository.save(cart));
    }


    @Override
    public JsonResponseDTO getUserCarts(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not Found"));
        List<Cart> userCarts=cartRepository.findByUserId(user.getUserId());
        return cartMapper.returnCartList(userCarts);
    }

    @Override
    public JsonResponseDTO removeFromCart(String email, Long cartId)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        Book book=bookRepository.findById(cart.getBookId()).orElseThrow(()->new BookNotFoundException("Book Not Found"));
        book.setQuantity(book.getQuantity()+cart.getQuantity());
        book.setCartBookQuantity(book.getCartBookQuantity()-cart.getQuantity());

        user.getCarts().remove(cart);
        cart.setBookId(null);
        cartRepository.delete(cart);

        return cartMapper.cartRemoved("Book removed from the cart");
    }

    @Override
    public JsonResponseDTO clearCart(String email)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Cart> carts=cartRepository.findByUserId(user.getUserId());

        user.getCarts().removeAll(carts);
        userRepository.save(user);

        for(Cart cart:carts)
        {
            Book book=bookRepository.findById(cart.getBookId()).orElseThrow(()->new BookNotFoundException("Book not found"));
            book.setQuantity(book.getQuantity()+ cart.getQuantity());
            book.setCartBookQuantity(book.getCartBookQuantity()- cart.getQuantity());
            bookRepository.save(book);
            cartRepository.delete(cart);
        }
        return cartMapper.cartRemoved("Cart cleared Successfully");
    }

    @Override
    public JsonResponseDTO getAllCarts()
    {
        List<Cart> carts=cartRepository.findAll();
        return cartMapper.returnCartList(carts);
    }
}
