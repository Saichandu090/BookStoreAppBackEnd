package com.app.bookstore.backend.repository;

import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long>
{
    WishList findByBook(Book book);
}
