package com.app.bookstore.backend.service;

import com.app.bookstore.backend.dto.JsonResponseDTO;
import com.app.bookstore.backend.dto.OrderDTO;

public interface OrderService
{
    JsonResponseDTO placeOrder(String email, OrderDTO orderDTO);

    JsonResponseDTO cancelOrder(String email,Long orderId);

    JsonResponseDTO getAllOrders();

    JsonResponseDTO getAllOrdersForUser(String email);
}
