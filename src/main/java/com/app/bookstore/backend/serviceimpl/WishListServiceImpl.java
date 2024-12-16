package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
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
    public JsonResponseDTO addToWishList(String email, Long bookId)
    {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not found"));
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));

        List<WishList> wishList=user.getWishList();
        if(wishList==null)
            user.setWishList(new ArrayList<>());
        WishList wishList1=new WishList();
        wishList1.setBook(book);
        wishList1.setUserId(user.getUserId());

        return wishListMapper.returnList(wishListRepository.save(wishList1));
    }

    @Override
    public JsonResponseDTO removeFromWishList(String email, Long wishListId)
    {
        WishList wishList=wishListRepository.findById(wishListId).orElseThrow(()->new RuntimeException("WishList not Found"));
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));

        user.getWishList().remove(wishList);
        wishListRepository.delete(wishList);
        return wishListMapper.wishListRemoved();
    }

    @Override
    public JsonResponseDTO getAllWishListItems(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        List<WishList> wishLists=user.getWishList();

        return wishListMapper.returnWishList(wishLists);
    }
}
