package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.UserDto;
import com.api.hotelreviewapplication.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, int id);
    void deleteUser(int id);
}
