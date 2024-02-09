package com.veersablog.BlogApp.services.impl;

import com.veersablog.BlogApp.entities.User;
import com.veersablog.BlogApp.exceptions.ResourceNotFoundException;
import com.veersablog.BlogApp.payloads.UserDto;
import com.veersablog.BlogApp.repositories.UserRepo;
import com.veersablog.BlogApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user" , "id" , userId));

        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user" , "id" , userId));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
       User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user" , "id" , userId));
       this.userRepo.delete(user);
    }

    public User userDtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto , User.class);
    }

    public UserDto userToUserDto(User user) {
        return this.modelMapper.map(user , UserDto.class);
    }
}
