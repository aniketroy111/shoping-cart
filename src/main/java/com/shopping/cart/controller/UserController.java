package com.shopping.cart.controller;

import com.shopping.cart.Response.ApiResponse;
import com.shopping.cart.exceptions.ResourceAlreadyExistException;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.User;
import com.shopping.cart.request.CreateUserRequest;
import com.shopping.cart.request.UpdateUserRequest;
import com.shopping.cart.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/{id}/user")
    public ResponseEntity<ApiResponse> getByUserId(@PathVariable Long id){
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("User fetch success",user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        try {
            User user = userService.createUser(createUserRequest);
            return ResponseEntity.ok(new ApiResponse("User created success",user));
        } catch ( ResourceAlreadyExistException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest userRequest,@PathVariable Long userId){
        try {
            User user = userService.updateUser(userRequest,userId);
            return ResponseEntity.ok(new ApiResponse("User update success",user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
