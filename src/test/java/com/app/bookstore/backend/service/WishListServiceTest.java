package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.WishListDTO;
import com.app.bookstore.backend.mapper.WishListMapper;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.User;
import com.app.bookstore.backend.model.WishList;
import com.app.bookstore.backend.repository.BookRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.repository.WishListRepository;
import com.app.bookstore.backend.serviceimpl.WishListServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishListServiceTest
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListServiceImpl wishListService;

    @MockBean
    private WishListMapper wishListMapper;

    private User user;
    private Book book;
    private WishList wishList;
    private WishListDTO wishListDTO;

    @BeforeEach
    public void init()
    {
        book=Book.builder()
                .cartBookQuantity(0)
                .bookId(1L)
                .bookName("Atomic Habits")
                .price(199.90)
                .bookLogo("Url")
                .author("James Clear")
                .description("Improve by 1%")
                .quantity(35)
                .build();

        wishListDTO=WishListDTO.builder()
                .bookId(book.getBookId()).build();

        wishList=WishList.builder()
                .id(1L)
                .book(book)
                .userId(1L).build();

        List<WishList> wishLists=new ArrayList<>();
        wishLists.addLast(wishList);

        user=User.builder()
                .userId(1L)
                .firstName("Sai")
                .lastName("Chandu")
                .email("marri@gmail.com")
                .dob(LocalDate.of(2002,8,24))
                .password("saichandu@45")
                .role("USER")
                .wishList(wishLists)
                .registeredDate(LocalDate.now()).build();
    }

    @Test
    public void WishListService_addToWishList_MustAddBookToWishList()
    {
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        when(wishListRepository.save(Mockito.any(WishList.class))).thenReturn(wishList);

        JsonResponseDTO responseDTO=wishListService.addToWishList(user.getEmail(),wishListDTO);
        System.out.println(responseDTO);
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.isResult()).isTrue();
    }

    @Test
    public void WishListService_removeFromWishList_MustRemoveBookToWishList()
    {
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        when(wishListRepository.saveAll(Mockito.any())).thenReturn(List.of(wishList));

        JsonResponseDTO responseDTO=wishListService.removeFromWishList(user.getEmail(),book.getBookId());
        System.out.println(responseDTO);
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.isResult()).isTrue();
    }

    @Test
    public void WishListService_getAllWishListItems_MustRetrieveAllWishListBooks()
    {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        JsonResponseDTO responseDTO=wishListService.getAllWishListItems(user.getEmail());
        System.out.println(responseDTO);
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.isResult()).isTrue();
        Assertions.assertThat(responseDTO.getData().size()).isEqualTo(1);
    }

    @Test
    public void WishListService_isInWishList_MustCheckWishList()
    {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

        JsonResponseDTO responseDTO=wishListService.isInWishList(user.getEmail(),book.getBookId());
        System.out.println(responseDTO);
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.isResult()).isTrue();
    }
}