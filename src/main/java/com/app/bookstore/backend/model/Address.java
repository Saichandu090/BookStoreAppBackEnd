package com.app.bookstore.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetName;
    private String city;
    private String state;
    private int pinCode;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Order> order;
}
