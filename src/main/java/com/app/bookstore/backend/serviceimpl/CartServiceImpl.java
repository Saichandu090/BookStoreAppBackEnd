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

        if(user.getCarts()==null)
            user.setCarts(new ArrayList<>());
        List<Cart> carts=user.getCarts();
        for(Cart cart: carts)
        {
                if(cart.getBook().equals(book))
                {
                    book.setQuantity(book.getQuantity()- 1);
                    book.setCartBookQuantity(book.getCartBookQuantity()+ 1);
                    return cartMapper.updateCartQuantity(cart);
                }
        }

        Cart cart=new Cart();
        cart.setUserId(user.getUserId());
        cart.setBook(book);
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

        Book book=bookRepository.findById(cart.getBook().getBookId()).orElseThrow(()->new BookNotFoundException("Book Not Found"));
        book.setQuantity(book.getQuantity()+cart.getQuantity());
        book.setCartBookQuantity(book.getCartBookQuantity()-cart.getQuantity());

        user.getCarts().remove(cart);
        cart.setBook(null);
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
            Book book=bookRepository.findById(cart.getBook().getBookId()).orElseThrow(()->new BookNotFoundException("Book not found"));
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

    @Override
    public JsonResponseDTO getUserCartById(String email, Long cartId)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart=cartRepository.findById(cartId).orElseThrow(()->new CartNotFoundException("Cart Not Found"));
        return cartMapper.returnCart(cart);
    }
}
