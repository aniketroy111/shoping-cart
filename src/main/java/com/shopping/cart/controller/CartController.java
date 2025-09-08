package com.shopping.cart.controller;

import com.shopping.cart.Response.ApiResponse;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Cart;
import com.shopping.cart.service.cart.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/carts")
@AllArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getCartById(cartId);
            return ResponseEntity.ok(new ApiResponse("Fetched Successfully",cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @DeleteMapping("/{cartId}/delete")
    public ResponseEntity<ApiResponse> deleteCartById(@PathVariable Long cartId){
        try {
            cartService.deleteCardById(cartId);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear Successfully",cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
