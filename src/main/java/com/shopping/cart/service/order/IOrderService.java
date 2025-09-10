package com.shopping.cart.service.order;

import com.shopping.cart.dto.OrderDto;
import com.shopping.cart.model.Order;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Long userId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto getOrderById(Long orderId);
}
