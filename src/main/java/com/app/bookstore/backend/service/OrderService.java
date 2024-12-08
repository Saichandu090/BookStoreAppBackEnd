package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.JsonResponseDTO;

public interface OrderService
{
    JsonResponseDTO placeOrder(String email);

    JsonResponseDTO cancelOrder(String email,Long orderId);

    JsonResponseDTO getAllOrders();

    JsonResponseDTO getAllOrdersForUser(String email);
}
