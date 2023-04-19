package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.UserDto;
import com.api.hotelreviewapplication.exception.UserNotFoundException;
import com.api.hotelreviewapplication.model.User;
import com.api.hotelreviewapplication.repository.UserRepository;
import com.api.hotelreviewapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " was not found"));

        return mapToDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("User with username " + userDto.getUsername() + " already exists.");
        }
        User user = new User();
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        userRepository.save(user);

        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        if (userRepository.findByUsername(userDto.getUsername()).isPresent() && !userDto.getUsername().equals(userToUpdate.getUsername())) {
            throw new RuntimeException("User with username " + userDto.getUsername() + " already exists.");
        }

        userToUpdate.setFirstname(userDto.getFirstname());
        userToUpdate.setLastname(userDto.getLastname());
        userToUpdate.setUsername(userDto.getUsername());
        userToUpdate.setPassword(userDto.getPassword());
        userRepository.save(userToUpdate);

        return mapToDto(userToUpdate);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRoleName(user.getRoleName());
        return userDto;
    }
}
