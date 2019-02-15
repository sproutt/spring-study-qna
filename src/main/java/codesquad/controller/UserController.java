package codesquad.controller;

import codesquad.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }


    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }


    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        users.stream().filter(user -> user.isSameUser(userId))
                .forEach(user -> model.addAttribute("user", user));

        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String modifyProfile(@PathVariable String userId, Model model) {
        users.stream().filter(user -> user.isSameUser(userId))
                .forEach(user -> model.addAttribute("user", user));

        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateForm(@PathVariable String userId, User updatedUser) {
        users.stream().filter(user -> user.isSameUser(userId))
                .forEach(user -> users.set(users.indexOf(user), updatedUser));

        return "redirect:/users";
    }
}
