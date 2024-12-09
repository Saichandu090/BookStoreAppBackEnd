package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.CartNotFoundException;
import com.app.bookstore.backend.exception.OrderNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.OrderMapper;
import com.app.bookstore.backend.model.*;
import com.app.bookstore.backend.repository.CartRepository;
import com.app.bookstore.backend.repository.OrderRepository;
import com.app.bookstore.backend.repository.UserRepository;
import com.app.bookstore.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService
{
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;

    private final OrderMapper orderMapper=new OrderMapper();

    @Override
    public JsonResponseDTO placeOrder(String email,Address address)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Cart cart=user.getCart();
        if(cart==null)
            throw new CartNotFoundException("Cart is Empty");
        if(!user.getAddresses().contains(address))
            throw new RuntimeException("Address Should not be Empty");
        List<Book> books=cart.getBooks();
        Order order=orderMapper.setOrder(books,cart.getTotalPrice());
        order.setOrderQuantity(cart.getQuantity());

        if(order.getUsers()==null)
            order.setUsers(new ArrayList<>());
        order.getUsers().add(user);
        if(user.getOrders()==null)
            user.setOrders(new ArrayList<>());
        user.getOrders().add(order);

        for(Book book:books)
        {
            if(book.getOrders()==null)
                book.setOrders(new ArrayList<>());
            book.getOrders().add(order);
        }
        if(!order.getCancelOrder()){
            for(Book book:books)
            {
                book.setCarts(null);
                //book.setCartBookQuantity(0);
            }
        }

        cart.setBooks(null);
        cart.setQuantity(0);
        cart.setTotalPrice(0.0);
        //cartRepository.delete(cart);
        Order savedOrder=orderRepository.save(order);
        return orderMapper.saveOrder(savedOrder);
    }

    @Override
    public JsonResponseDTO cancelOrder(String email, Long orderId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found"));

        if(user.getOrders().contains(order))
        {
            order.setCancelOrder(true);
            List<Book> books=order.getBooks();
            for(Book book:books)
            {
                book.setQuantity(book.getQuantity()+book.getCartBookQuantity());
            }
        }

        Order cancelledOrder=orderRepository.save(order);

        return orderMapper.saveOrder(cancelledOrder);
    }

    @Override
    public JsonResponseDTO getAllOrders()
    {
        List<Order> orderList=orderRepository.findAll();
        return orderMapper.sendOrderList(orderList,"OrderList for Admin Retrieved Successfully!!");
    }

    @Override
    public JsonResponseDTO getAllOrdersForUser(String email)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        List<Order> orderList=user.getOrders();
        return orderMapper.sendOrderList(orderList,"OrderList for User "+email+" retrieved Successfully!!");
    }
}
