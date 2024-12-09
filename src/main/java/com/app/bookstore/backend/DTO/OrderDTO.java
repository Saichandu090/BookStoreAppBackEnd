package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO
{
    private Long userId;
    private List<Book> books;
    private int quantity;
    private Address address;
}
