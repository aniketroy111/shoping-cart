package com.shopping.cart.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
