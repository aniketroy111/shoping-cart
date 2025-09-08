package com.shopping.cart.service.cart;

import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartItem;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.CartItemRepository;
import com.shopping.cart.repository.CartRepository;
import com.shopping.cart.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public Cart addItemToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.findProductById(productId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item-> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if(cartItem.getId() == null){
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }

        cartItem.setTotalPrice();
        CartItem newItem = cartItemRepository.save(cartItem);
        BigDecimal totalAmount = cartService.getTotalAmount(cartId);
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartService.getCartById(cartId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item-> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Item not found"));

        cart.getCartItems().remove(cartItem);
        BigDecimal totalAmount = cartService.getTotalAmount(cartId);
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItemQuantity(Long cartId, Long itemId, Integer quantity){
        Cart cart = cartService.getCartById(cartId);

        cart.getCartItems().stream()
                .filter(item-> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cartService.getTotalAmount(cartId);
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }
}
