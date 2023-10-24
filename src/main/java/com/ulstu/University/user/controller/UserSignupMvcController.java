package com.ulstu.University.user.controller;


import com.ulstu.University.controller.DepartmentDto;
import com.ulstu.University.user.model.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ulstu.University.user.model.User;
import com.ulstu.University.user.model.UserSignupDto;
import com.ulstu.University.user.service.UserService;
import com.ulstu.University.util.validation.ValidationException;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping(UserSignupMvcController.SIGNUP_URL)
public class UserSignupMvcController {
    public static final String SIGNUP_URL = "/signup";

    private final UserService userService;
    private UserRole userRole;

    public UserSignupMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("userDto", new UserSignupDto());
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("userDto") @Valid UserSignupDto userSignupDto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "signup";
        }
        try {
            final User user = userService.createUser(
                    userSignupDto.getLogin(), userSignupDto.getPassword(), userSignupDto.getPasswordConfirm(),
                    userRole.valueOf(userSignupDto.getUserRole()));
            return "redirect:/login?created=" + user.getLogin();
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getMessage());
            return "signup";
        }
    }
}
