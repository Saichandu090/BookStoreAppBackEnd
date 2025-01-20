package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.WishListDTO;
import com.app.bookstore.backend.exception.BookNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.WishListMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.model.WishList;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.repository.WishListRepository;
import com.app.bookstore.backend.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService
{
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private WishListRepository wishListRepository;

    private final WishListMapper wishListMapper=new WishListMapper();

    @Override
    public JsonResponseDTO addToWishList(String email, WishListDTO wishListDTO)
    {
        Book book=bookRepository.findById(wishListDTO.getBookId()).orElseThrow(()->new BookNotFoundException("Book not found"));
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));

        List<WishList> wishList=user.getWishList();
        if(wishList==null)
            user.setWishList(new ArrayList<>());
        WishList wishList1=WishList.builder()
                        .book(book)
                                .userId(user.getUserId()).build();
        user.getWishList().add(wishList1);

        return wishListMapper.returnList(wishListRepository.save(wishList1),book.getBookName());
    }

    @Override
    public JsonResponseDTO removeFromWishList(String email, Long bookId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not Found"));
        List<WishList> wishLists=user.getWishList();
        wishLists.removeIf(w -> w.getBook().equals(book));

        wishListRepository.saveAll(wishLists);
        return wishListMapper.wishListRemoved(book.getBookName());
    }

    @Override
    public JsonResponseDTO getAllWishListItems(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        List<WishList> wishLists=user.getWishList();

        List<Book> books=new ArrayList<>();

        for(WishList w: wishLists)
        {
            books.add(w.getBook());
        }

        return wishListMapper.returnWishList(books);
    }

    @Override
    public JsonResponseDTO isInWishList(String email, Long bookId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not found"));
        List<WishList> wishLists=user.getWishList();

        for(WishList w:wishLists)
        {
            if(w.getBook().equals(book))
            {
                return wishListMapper.inWishList();
            }
        }
        return wishListMapper.notInWishList();
    }
}
