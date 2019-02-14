package codesquad.Controller;

import codesquad.Model.User;
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

    @GetMapping("/form")
    public String form() {
        return "/users/form";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {

        users.stream().filter(user -> user.isSameUser(userId)).forEach(user -> model.addAttribute("user", user));

        return "users/profile";
    }
}
