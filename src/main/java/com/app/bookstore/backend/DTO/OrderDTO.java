package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO
{
    private int quantity;
    private double price;
    private Long addressId;
}
