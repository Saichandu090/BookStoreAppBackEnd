package com.app.bookstore.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "Books_Cart",
    joinColumns = {
            @JoinColumn(name = "cart_id",referencedColumnName = "id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "book_id",referencedColumnName = "id")
    })
    private List<Book> bookList;

    private Long quantity;
    private Long totalPrice;
}
