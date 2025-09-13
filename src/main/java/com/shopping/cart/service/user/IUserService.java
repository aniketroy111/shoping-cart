package com.shopping.cart.service.user;

import com.shopping.cart.model.User;
import com.shopping.cart.request.CreateUserRequest;
import com.shopping.cart.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long id);
    User createUser(CreateUserRequest userDto);
    User updateUser(UpdateUserRequest userRequest, Long userId);
    void deleteUser(Long id);
}
