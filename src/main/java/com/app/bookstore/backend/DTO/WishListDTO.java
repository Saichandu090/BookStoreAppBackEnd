package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Book;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO
{
    private Long bookId;
}
