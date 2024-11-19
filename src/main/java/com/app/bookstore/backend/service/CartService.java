package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.*;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.exception.CartNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.CartRepository;
import com.app.bookstore.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    public CartResponseDTO addToCart(UserDetails userDetails, CartRequestDTO requestDTO)
    {
        User cartUser=userRepository.findByEmail(userDetails.getUsername());
        if(cartUser==null)
            throw new UserNotFoundException("User Not Found 404");

        if(cartUser.getCart()==null)
        {
            Cart cart = new Cart();
            cartUser.setCart(cart);
            cart.setUser(cartUser);
            List<Long> bookList = requestDTO.getBookId();
            List<Book> books =new ArrayList<>();
            double cartPrice=0.0;
            for (Long l : bookList) {
                Book myBook = bookRepository.findById(l).orElseThrow(() -> new BookNotFoundException("Book Not Found 404"));
                books.add(myBook);
                cartPrice = cartPrice + myBook.getPrice();
            }
            cart.setBookList(books);
            cart.setQuantity((long) bookList.size());
            cart.setTotalPrice((long)cartPrice);

            return cartToResponseDTO(cartRepository.save(cart));
        }
        else {
            Cart cart = cartUser.getCart();
            List<Long> bookList = requestDTO.getBookId();
            List<Book> books =cartRepository.findBooksByCartId(cart.getId());
            double cartPrice=cart.getTotalPrice();
            for (Long l : bookList)
            {
                Book myBook = bookRepository.findById(l).orElseThrow(() -> new BookNotFoundException("Book Not Found 404"));
                books.add(myBook);
                cartPrice = cartPrice + myBook.getPrice();
            }
            cart.setBookList(books);
            cart.setQuantity((long) bookList.size()+books.size());
            cart.setTotalPrice((long) cartPrice);

            return cartToResponseDTO(cartRepository.save(cart));
        }
    }

    public CartResponseDTO cartToResponseDTO(Cart cart)
    {
        CartResponseDTO responseDTO=new CartResponseDTO();
        responseDTO.setId(cart.getId());
        responseDTO.setQuantity(cart.getQuantity());
        responseDTO.setTotalPrice(cart.getTotalPrice());
        List<Book> bookList=cart.getBookList();
        List<BookResponseDTO> bookResponseDTOS=new ArrayList<>();
        for(Book book:bookList)
        {
            bookResponseDTOS.add(bookService.bookToResponseDTO(book));
        }
        responseDTO.setBooksList(bookResponseDTOS);
        return responseDTO;
    }

    public List<CartResponseDTO> getAllCarts()
    {
        List<Cart> cartList=cartRepository.findAll();
        List<CartResponseDTO> responseDTOS=new ArrayList<>();
        for(Cart c : cartList)
        {
            responseDTOS.add(cartToResponseDTO(c));
        }
        return responseDTOS;
    }

    public String removeFromCart(Long cartId)
    {
        Cart cart=cartRepository.findById(cartId).orElseThrow(()->new CartNotFoundException("Cart Not Found 404"));
        cartRepository.delete(cart);
        return "Cart with Id "+cartId+" has been deleted";
    }

    public String deleteUserCart(UserDetails userDetails)
    {
        User user=userRepository.findByEmail(userDetails.getUsername());
        if(user==null)
            throw new UserNotFoundException("User Not Found 404");

        cartRepository.deleteByUserId(user.getId());
        return "Cart with user Id "+user.getId()+" has been deleted";
    }

    public CartResponseDTO getALlUserItems(UserDetails userDetails)
    {
        User user=userRepository.findByEmail(userDetails.getUsername());
        if(user==null)
            throw new UserNotFoundException("User Not Found 404");

        Cart cart=cartRepository.findCartByUserId(user.getId());
        return cartToResponseDTO(cart);
    }
}
