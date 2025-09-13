package com.shopping.cart.controller;

import com.shopping.cart.Response.ApiResponse;
import com.shopping.cart.dto.OrderDto;
import com.shopping.cart.service.order.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders")
@AllArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto orderDto = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("Fetch order",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{userId}/order-of-user")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> orderDtos = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Order fetch successfully",orderDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/{userId}/order-place")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId){
        try {
            OrderDto orderDto = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order place success",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
