package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.OrderDTO;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Order;
import com.app.bookstore.backend.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{
    public JsonResponseDTO saveOrder(Order order,String message)
    {
        return JsonResponseDTO.builder()
                .message(message)
                .data(List.of(order))
                .result(true).build();
    }

    public JsonResponseDTO sendOrderList(List<Order> orderList,String message)
    {
        return JsonResponseDTO.builder()
                .message(message)
                .data(orderList)
                .result(true).build();
    }

    public Order createOrder(OrderDTO orderDTO)
    {
        return Order.builder()
                .cancelOrder(false)
                .orderDate(LocalDate.now())
                .orderPrice(orderDTO.getPrice())
                .orderQuantity(orderDTO.getQuantity()).build();
    }
}
