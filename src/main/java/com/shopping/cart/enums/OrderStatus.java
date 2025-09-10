package com.shopping.cart.enums;

public enum OrderStatus {
    PENDING,       // Order placed, waiting for payment/confirmation
    CONFIRMED,     // Payment confirmed / order accepted
    SHIPPED,       // Order shipped from warehouse
    DELIVERED,     // Customer received the product
    CANCELLED,     // Order was cancelled
    RETURNED
}
