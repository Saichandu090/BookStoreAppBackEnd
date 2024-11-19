package com.app.bookstore.backend.repository;

import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>
{
    @Query("SELECT c.bookList FROM Cart c WHERE c.id = :cartId")
    List<Book> findBooksByCartId(@Param("cartId") Long cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findCartByUserId(@Param("userId") Long userId);
}
