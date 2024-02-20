package com.springboot.controller;

import com.springboot.dto.UserDto;
import com.springboot.entity.User;
import com.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class AuthController {

    private UserService userService;

    //Handle method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    //Handle method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Handle method to handle registration page request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        //Create model to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    //Handle method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute(name = "user") UserDto userDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "register";
        }
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser==null && userDto.getEmail()==null && userDto.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        userService.SaveUser(userDto);
        return "redirect:/register?success";
    }

    //Handle method to handle List of users page request
    @GetMapping("/users")
    public String users(Model model){
        //Create model to store form data
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
