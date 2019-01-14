package codesquad.controller;

import codesquad.domain.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String readUsers(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/")
    public String main() {
        return "/index";
    }
}
