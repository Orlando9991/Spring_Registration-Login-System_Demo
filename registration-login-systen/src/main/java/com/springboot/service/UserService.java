package com.springboot.service;

import com.springboot.dto.UserDto;
import com.springboot.entity.User;

import java.util.List;

public interface UserService {
    void SaveUser(UserDto user);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
