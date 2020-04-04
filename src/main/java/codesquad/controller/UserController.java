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

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String get(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String get(@PathVariable String userId, Model model) {
        User user = new User();
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            if (user.isSameUser(userId)) break;
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/form")
    public String show() {
        return "user/form";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = new User();
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            if (user.isSameUser(userId)) break;
        }
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, User user, Model model) {
        User beforeUser = new User();
        for (int i = 0; i < users.size(); i++) {
            beforeUser = users.get(i);
            if (beforeUser.isSameUser(userId)) break;
        }
        if (beforeUser.isSamePassword(user)) {
            beforeUser.changeUserInfo(user);
            return "redirect:/users";
        }
        model.addAttribute("user", beforeUser);
        return "user/updateForm";
    }
}
