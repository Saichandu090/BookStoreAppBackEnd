package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;
import com.app.bookstore.backend.model.Book;
import com.app.bookstore.backend.model.Order;
import com.app.bookstore.backend.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{
    public Order setOrder(List<Book> books, double totalPrice)
    {
        Order order=new Order();
        order.setOrderDate(LocalDate.now());
        order.setCancelOrder(false);
        order.setBooks(new ArrayList<>(books));
        order.setOrderPrice(totalPrice);
        return order;
    }

    public JsonResponseDTO saveOrder(Order order)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Order Placed Successfully!!");
        responseDTO.setData(List.of(order));
        return responseDTO;
    }

    public JsonResponseDTO sendOrderList(List<Order> orderList,String message)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage(message);
        responseDTO.setData(orderList);
        return responseDTO;
    }
}
