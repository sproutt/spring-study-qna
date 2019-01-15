package codesquad.controller;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();
    private Long id = 0L;

    @PostMapping("/create")
    public String create(User user) {
        user.setId(++id);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        return "users/profile";
    }
}
