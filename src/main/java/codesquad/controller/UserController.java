package codesquad.controller;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/user/form")
    public String showForm() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (findUser(user, userId)) {
                model.addAttribute("user", user);
            }
        }
        return "user/profile";
    }

    private boolean findUser(User user, String userId) {
        return user.getUserId().equals(userId);
    }

}
