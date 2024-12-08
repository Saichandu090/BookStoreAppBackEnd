package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.mapper.OrderMapper;
import com.app.bookstore.backend.repository.OrderRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService
{
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    private final OrderMapper orderMapper=new OrderMapper();

    @Override
    public JsonResponseDTO placeOrder(String email)
    {
        return null;
    }

    @Override
    public JsonResponseDTO cancelOrder(String email, Long orderId)
    {
        return null;
    }

    @Override
    public JsonResponseDTO getAllOrders()
    {
        return null;
    }

    @Override
    public JsonResponseDTO getAllOrdersForUser(String email)
    {
        return null;
    }
}
