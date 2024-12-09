package com.app.bookstore.backend.service;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.Address;

public interface OrderService
{
    JsonResponseDTO placeOrder(String email,Long addressId);

    JsonResponseDTO cancelOrder(String email,Long orderId);

    JsonResponseDTO getAllOrders();

    JsonResponseDTO getAllOrdersForUser(String email);
}
