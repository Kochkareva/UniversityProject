package com.ulstu.University.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ulstu.University.user.model.UserDto;
import com.ulstu.University.user.model.UserRole;
import com.ulstu.University.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserService userService;

    public UserMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Secured({UserRole.AsString.ADMIN})
    public String getUsers(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Model model) {
        final Page<UserDto> users = userService.findAllPages(page, size)
                .map(UserDto::new);
        model.addAttribute("users", users);
        final int totalPages = users.getTotalPages();
        final List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", pageNumbers);
        model.addAttribute("totalPages", totalPages);
        return "users";
    }
}
