package com.greenfrog.qna.controller;

import com.greenfrog.qna.domain.User;
import com.greenfrog.qna.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String showList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @PostMapping("")
    public String signUp(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, String currentPassword, String newPassword, String name, String email) {
        User user = userRepository.findByUserId(userId);
        if (user.isSamePassword(currentPassword)) {
            user.update(newPassword, name, email);
            userRepository.save(user);
            return "redirect:/users";
        }
        return "redirect:/users/" + userId + "/form";
    }
}
