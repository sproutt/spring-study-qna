package codesquad.controller;

import codesquad.domain.User;
import codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public String showAll() {
        List<User> users = userService.findAll();
        return "/user/list";
    }

    @GetMapping("/user/create")
    public String createForm() {
        return "/user/form";
    }
}
