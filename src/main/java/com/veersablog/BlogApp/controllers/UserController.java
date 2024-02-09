package com.veersablog.BlogApp.controllers;

import com.veersablog.BlogApp.payloads.ApiResponse;
import com.veersablog.BlogApp.payloads.UserDto;
import com.veersablog.BlogApp.services.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
     UserDto userDto1 = this.userService.createUser(userDto);
     return new ResponseEntity<>(userDto1 , HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto , @PathVariable Integer userId) {
        UserDto userDto1 = this.userService.updateUser(userDto , userId);
       return ResponseEntity.ok(userDto1);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully" , true) , HttpStatus.OK);
    }
}
