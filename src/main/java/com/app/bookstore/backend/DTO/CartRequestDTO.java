package com.app.bookstore.backend.DTO;

import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO
{
    private Long bookId;
    private int quantity;
}
