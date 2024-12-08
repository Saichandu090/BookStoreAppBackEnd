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

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setBooks(new ArrayList<>());
            cart.setQuantity(1);
            cart.setTotalPrice(book.getPrice());
            cart.getBooks().add(book);
        } else {
            if (!cart.getBooks().contains(book)) {
                cart.getBooks().add(book);
            }
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + book.getPrice());
        }

        if (book.getCarts() == null) {
            book.setCarts(new ArrayList<>());
        }
        if (!book.getCarts().contains(cart)) {
            book.getCarts().add(cart);
            book.setCartBookQuantity(1);
        }else {
            book.setCartBookQuantity(book.getCartBookQuantity()+1);
        }

        if(book.getQuantity()>0) {
            book.setQuantity(book.getQuantity() - 1);
        }else {
            throw new BookNotFoundException("Book Out of Stock");
        }

        return cartMapper.saveCart(cartRepository.save(cart));
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
    public JsonResponseDTO removeFromCart(String email, Long bookId)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Cart cart=user.getCart();
        if(cart==null)
        {
            throw new CartNotFoundException("Cart is Empty");
        }
        else
        {
            if(cart.getBooks().contains(book))
            {
                if(book.getCartBookQuantity()==0) {
                    throw new CartNotFoundException("Book is not inside the cart anymore");
                }
                cart.getBooks().remove(book);
                cart.setTotalPrice(cart.getTotalPrice()-book.getPrice());
                if(cart.getTotalPrice()<0)
                    cart.setTotalPrice(0.0);
                cart.setQuantity(cart.getQuantity()-1);
                book.setCartBookQuantity(book.getCartBookQuantity() - 1);
                book.setQuantity(book.getQuantity() + 1);
            }else
                throw new BookNotFoundException("Book is not present inside the cart");
        }

        bookRepository.save(book);
        if(book.getCartBookQuantity()==0){
            cart.getBooks().remove(book);
            book.getCarts().remove(cart);
        }
        Cart savedCart=cartRepository.save(cart);
        return cartMapper.saveCart(savedCart);
    }

    @Override
    public JsonResponseDTO clearCart(String email)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart=user.getCart();
        if(cart==null)
            throw new CartNotFoundException("Cart is Already Empty");
        if(cart.getBooks().isEmpty()){
            return cartMapper.cartEmpty();
        }else{
            List<Book> books=cart.getBooks();
            for(Book book: books)
            {
                book.setQuantity(book.getQuantity()+book.getCartBookQuantity());
                book.setCartBookQuantity(0);
                book.getCarts().remove(cart);
            }
            cart.setBooks(null);
            cart.setQuantity(0);
            cart.setTotalPrice(0);
        }
        return cartMapper.saveCart(cartRepository.save(cart));
    }
}
