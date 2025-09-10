package com.shopping.cart.service.order;

import com.shopping.cart.dto.OrderDto;
import com.shopping.cart.enums.OrderStatus;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.*;
import com.shopping.cart.repository.OrderRepository;
import com.shopping.cart.repository.ProductRepository;
import com.shopping.cart.service.cart.ICartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ICartService cartService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public OrderDto placeOrder(Long userId) {
        // get cart by user
        Cart cart = cartService.getCartByUserId(userId);
        // create the order by above cart
        Order order = createOrder(cart);
        // finding the order items that user want to order
        List<OrderItem> orderItems = createOrderItems(cart,order);
        // set the order item in order
        order.setOrderItems(new HashSet<>(orderItems));
        // total amount that order item
        order.setTotalAmount(getTotalAmountOfOrder(orderItems));

        Order savedOrder = orderRepository.save(order);
        // after order complete clear the cart
        cartService.clearCart(cart.getId());
        return convertToDto(order);
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal getTotalAmountOfOrder(List<OrderItem> orderItems){
        return orderItems.stream().map(orderItem-> orderItem.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity()))).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Cart cart,Order order){

        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            return orderItem;
        }).toList();
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    private OrderDto convertToDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }

}
