package com.shopping.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<OrderDto> orders;
    private CartDto cart;
}
