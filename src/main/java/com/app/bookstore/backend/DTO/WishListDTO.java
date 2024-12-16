package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO
{
    private Long bookId;
}
