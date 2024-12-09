package com.app.bookstore.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book
{
    @Id
    private Long bookId;
    private String bookName;
    private String author;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer cartBookQuantity;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "cart_book",
            joinColumns = @JoinColumn(name = "book_id",referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "cart_id",referencedColumnName = "cartId"))
    @JsonManagedReference
    private List<Cart> carts;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "book_orders",
            joinColumns = @JoinColumn(name = "book_id",referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "order_id",referencedColumnName = "orderId"))
    @JsonManagedReference
    private List<Order> orders;
}
