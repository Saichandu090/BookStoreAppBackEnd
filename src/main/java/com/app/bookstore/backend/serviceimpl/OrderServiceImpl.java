package com.app.bookstore.backend.serviceimpl;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.DTO.OrderDTO;
import com.app.bookstore.backend.exception.*;
import com.app.bookstore.backend.mapper.OrderMapper;
import com.app.bookstore.backend.model.*;
import com.app.bookstore.backend.repository.*;
import com.app.bookstore.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private CartRepository cartRepository;

    private final OrderMapper orderMapper=new OrderMapper();

    @Override
    public JsonResponseDTO placeOrder(String email, OrderDTO orderDTO)
    {
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        List<Address> userAddressList=user.getAddresses();
        Address address=addressRepository.findById(orderDTO.getAddressId()).orElseThrow(()->new AddressNotFoundException("Address not associated with User"));

        if(!userAddressList.contains(address))
            throw new RuntimeException("Address Required!!");
        List<Cart> carts=user.getCarts();

        Order order=new Order();
        order.getCarts().addAll(carts);
        order.setAddressId(address.getAddressId());
        order.setUserId(user.getUserId());
        order.setOrderDate(LocalDate.now());
        order.setCancelOrder(false);
        order.setOrderPrice(orderDTO.getPrice());
        order.setOrderQuantity(orderDTO.getQuantity());

        if(address.getOrder()==null)
            address.setOrder(new ArrayList<>());
        address.getOrder().add(order);

        user.getCarts().clear();
        //userRepository.save(user); If user is also saved then it's saving twice so don't do that
        cartRepository.saveAll(carts);

        Order savedOrder=orderRepository.save(order);
        return orderMapper.saveOrder(savedOrder,"Order with Id "+savedOrder.getOrderId()+" placed successfully!!!");
    }

    @Override
    public JsonResponseDTO cancelOrder(String email, Long orderId)
    {
        //User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found"));

        if(order.getCancelOrder()){
            throw new RuntimeException("Order already Cancelled!!");
        }
        List<Cart> carts=order.getCarts();
        for(Cart cart: carts)
        {
            Book book=bookRepository.findById(cart.getBook().getBookId()).orElseThrow(()->new BookNotFoundException("Book Not Found"));
            book.setQuantity(book.getQuantity()+ cart.getQuantity());
            book.setCartBookQuantity(book.getCartBookQuantity()- cart.getQuantity());
            //order.getCarts().remove(cart);
            cartRepository.save(cart);
        }
        order.setCancelOrder(true);

        return orderMapper.saveOrder(orderRepository.save(order),"Order with Id "+order.getOrderId()+" cancelled successfully!!");
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
        List<Order> orderList=orderRepository.findByUserId(user.getUserId());
        return orderMapper.sendOrderList(orderList,"OrderList for User "+email+" retrieved Successfully!!");
    }
}
