package com.springboot.security;

import com.springboot.entity.User;
import com.springboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(usernameOrEmail);

        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    user.getRoles().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
        }

        throw new UsernameNotFoundException("Invalid email or password");
    }
}
