package com.springboot.service.impl;

import com.springboot.dto.UserDto;
import com.springboot.entity.Role;
import com.springboot.entity.User;
import com.springboot.repository.RoleRepository;
import com.springboot.repository.UserRepository;
import com.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void SaveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() +" "+ userDto.getLastName());
        user.setEmail(userDto.getEmail());
        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExistance();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user)-> {
            UserDto userDto = new UserDto();

            userDto.setId(user.getId());
            String [] names = user.getName().split(" ");
            userDto.setFirstName(names[0]);
            userDto.setLastName(names[1]);
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());

            return userDto;
        }).collect(Collectors.toList());
    }

    private Role checkRoleExistance(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
