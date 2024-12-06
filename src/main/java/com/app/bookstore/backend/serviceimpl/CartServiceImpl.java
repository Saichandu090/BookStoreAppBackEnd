package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.CartRequestDTO;
import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.CartMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.CartRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService
{
    @Autowired
    private  CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private final CartMapper cartMapper=new CartMapper();

    @Override
    public JsonResponseDTO addToCart(String email, CartRequestDTO requestDTO)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        Book book=bookRepository.findById(requestDTO.getBookId()).orElseThrow(()->new BookNotFoundException("Book not found"));
        if(user.getCart()==null)
        {
            Cart cart = cartMapper.addToCart(user, book, requestDTO.getQuantity());
            user.setCart(cart);
            book.setCarts(cart);
            book.setCartBookQuantity(requestDTO.getQuantity());
            return cartMapper.saveCart(cartRepository.save(cart));
        }
        else {
            Cart cart=user.getCart();
            cart.getBooks().add(book);
            cart.setQuantity(cart.getQuantity()+requestDTO.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice()+book.getPrice()* requestDTO.getQuantity());
            book.setCarts(cart);
            book.setCartBookQuantity(book.getCartBookQuantity()+requestDTO.getQuantity());
            bookRepository.save(book);
            return cartMapper.saveCart(cartRepository.save(cart));
        }
    }

    @Override
    public JsonResponseDTO getUserCart(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        if(user.getCart()!=null)
        {
            Cart cart=user.getCart();
            return cartMapper.returnCart(cart);
        }
        else
        {
            return cartMapper.cartEmpty();
        }
    }

    @Override
    public JsonResponseDTO removeFromCart(String email, Long bookId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        Cart cart=user.getCart();
        List<Book> books=cart.getBooks();
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not found"));
        books.remove(book);
        cart.setBooks(books);
        cart.setQuantity(cart.getQuantity()-1);
        cart.setTotalPrice(cart.getTotalPrice()-book.getPrice());
        updateQuantity(book.getBookId(),1);
        bookRepository.save(book);
        return cartMapper.updateCart(cartRepository.save(cart));
    }

    public void updateQuantity(Long bookId, int quantity)
    {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not found"));
        int cartBookQuantity=book.getCartBookQuantity();
        if(cartBookQuantity>=1){
            book.setCartBookQuantity(cartBookQuantity-quantity);
            if(book.getCartBookQuantity()==0)
                book.setCarts(null);
        }else {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.save(book);
    }

    @Override
    public JsonResponseDTO clearCart(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        List<Book> books=user.getCart().getBooks();
        for(Book book:books)
        {
            book.setCarts(null);
            book.setCartBookQuantity(0);
            bookRepository.save(book);
        }
        Cart cart=user.getCart();
        cart.setBooks(List.of());
        cart.setQuantity(0);
        cart.setTotalPrice(0);
        userRepository.save(user);
        return cartMapper.removeCart(cartRepository.save(cart));
    }
}
