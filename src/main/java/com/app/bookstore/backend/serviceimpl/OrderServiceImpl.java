package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.exception.AddressNotFoundException;
import com.app.bookstore.backend.exception.CartNotFoundException;
import com.app.bookstore.backend.exception.OrderNotFoundException;
import com.app.bookstore.backend.exception.UserNotFoundException;
import com.app.bookstore.backend.mapper.OrderMapper;
import com.app.bookstore.backend.model.*;
import com.app.bookstore.backend.repository.*;
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
    private BookRepository bookRepository;
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;

    private final OrderMapper orderMapper=new OrderMapper();

    @Override
    public JsonResponseDTO placeOrder(String email,Long addressId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Cart cart=user.getCart();
        if(cart==null || cart.getQuantity()==0)
            throw new CartNotFoundException("Cart is Empty");

        Address address=addressRepository.findById(addressId).orElseThrow(()->new AddressNotFoundException("Address Not Found!!"));
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
            }
        }

        cart.setBooks(null);
        cart.setQuantity(0);
        cart.setTotalPrice(0.0);
        Order savedOrder=orderRepository.save(order);
        return orderMapper.saveOrder(savedOrder,"Order Placed Successfully!!");
    }

    @Override
    public JsonResponseDTO cancelOrder(String email, Long orderId)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found"));

        if(order.getCancelOrder())
            throw new OrderNotFoundException("Order is not active to cancel");

        if(user.getOrders().contains(order))
        {
            order.setCancelOrder(true);
            List<Book> books=order.getBooks();
            for(Book book:books)
            {
                book.setQuantity(book.getQuantity()+1);
                book.setCartBookQuantity(book.getCartBookQuantity()-1);  // Need to still work on cancel order to update book entity
                bookRepository.save(book);
            }
        }
        Order cancelledOrder=orderRepository.save(order);

        return orderMapper.saveOrder(cancelledOrder,"Order Cancelled Successfully!!");
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
