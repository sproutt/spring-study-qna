package codesquad.controller;

import codesquad.domain.User;
import codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user/create")
    public String create(@ModelAttribute User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) throws IOException {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showUserInfo(@PathVariable String userId, Model model) {
        User user = userService.findByUserId(userId);
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }
}
