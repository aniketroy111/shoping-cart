package com.shopping.cart.exceptions;


public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message){
        super(message);
    }
}
