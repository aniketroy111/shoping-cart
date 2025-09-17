package com.shopping.cart.service.cart;

import com.shopping.cart.dto.CartDto;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartItem;
import com.shopping.cart.model.User;
import com.shopping.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public BigDecimal getTotalAmount(Long id) {
        Cart cart = getCartById(id);
        return cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public void deleteCardById(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository :: delete,()->{ throw new ResourceNotFoundException("Cart not found");});

    }

    @Transactional
    @Override
    public Cart clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.getCartItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        Cart savedCart =  cartRepository.save(cart);
        return savedCart;
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(()->
        {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalAmount(BigDecimal.ZERO);
            return cartRepository.save(cart);
        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    @Override
    public CartDto convertToDto(Cart cart){
        return modelMapper.map(cart,CartDto.class);
    }

}
