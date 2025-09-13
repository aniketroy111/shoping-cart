package com.shopping.cart.service.user;

import com.shopping.cart.exceptions.ResourceAlreadyExistException;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.User;
import com.shopping.cart.repository.UserRepository;
import com.shopping.cart.request.CreateUserRequest;
import com.shopping.cart.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest userRequest) {
       return Optional.of(userRequest)
               .filter(user -> !userRepository.existByEmail(userRequest.getEmail()))
               .map(req ->{
                   User user = new User();
                   user.setId(req.getId());
                   user.setFirstname(req.getFirstname());
                   user.setLastname(req.getLastname());
                   user.setEmail(req.getEmail());
                   user.setPassword(req.getPassword());
                   return userRepository.save(user);
               }).orElseThrow(()-> new ResourceAlreadyExistException(userRequest.getEmail()+" already exist"));
    }

    @Override
    public User updateUser(UpdateUserRequest userRequest, Long userId) {
        return userRepository.findById(userId).map(existing->{
            existing.setFirstname(userRequest.getFirstname());
            existing.setLastname(userRequest.getLastname());
            return userRepository.save(existing);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,()->{ throw new ResourceNotFoundException("User not found");});
    }
}
