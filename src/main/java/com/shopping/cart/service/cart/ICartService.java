package com.shopping.cart.service.cart;

import com.shopping.cart.dto.CartDto;
import com.shopping.cart.model.Cart;
import com.shopping.cart.model.User;

import java.math.BigDecimal;

public interface ICartService {

    public Cart getCartById(Long id);
    public BigDecimal getTotalAmount(Long id);
    public void deleteCardById(Long id);
    public Cart clearCart(Long cartId);
    public Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);

    CartDto convertToDto(Cart cart);
}
