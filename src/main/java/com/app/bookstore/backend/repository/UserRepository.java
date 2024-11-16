package com.app.bookstore.backend.repository;

import com.app.bookstore.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
    User findByEmail(String email);

    User findByFirstName(String firstName);
}
