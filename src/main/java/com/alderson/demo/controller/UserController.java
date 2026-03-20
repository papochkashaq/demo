package com.alderson.demo.controller;

import java.util.UUID;

import com.alderson.demo.model.User;
import com.alderson.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping("/create")
    public String addUser(@Valid @ModelAttribute("user") User user) {
        try {
            userService.addUser(user);
            return "redirect:/users";
        } catch (Exception e) {
            return "redirect:/users/email-error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable UUID id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable UUID id, @Valid @ModelAttribute("user") User user) {
        try {
            userService.addUser(user);
            return "redirect:/users";
        } catch (Exception e) {
            return "redirect:/users/email-error";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam UUID id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/email-error")
    public String emailError() {
        return "email-error";
    }
}
