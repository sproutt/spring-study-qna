package codesquad.controller;

import codesquad.domain.User;
import codesquad.exception.NoSuchUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private final List<User> users = new ArrayList<>();
    private Long id = 1L;

    @PostMapping("/users")
    public String create(@ModelAttribute User user) {
        user.createId(id++);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showUserInfo(@PathVariable String userId, Model model) {
        User user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findAny()
                .orElseThrow(NoSuchUserException::new);
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }
}
