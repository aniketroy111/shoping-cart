package com.shopping.cart.service.cart;

import com.shopping.cart.model.Cart;

public interface ICartItemService {
    public Cart addItemToCart(Long cartId,Long productId,Integer quantity);
    public Cart removeItemFromCart(Long cartId,Long itemId);
    public Cart updateItemQuantity(Long cartId, Long productId, Integer quantity);
}
