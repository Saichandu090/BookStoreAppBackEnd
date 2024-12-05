package com.app.bookstore.backend.repository;

import com.app.bookstore.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>
{
    Book findByBookName(String bookName);
}
