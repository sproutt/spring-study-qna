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
        for (User user : users) {
            if (user.isSameUser(userId)) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "/users";
    }

    @GetMapping("/users/form")
    public String show() {
        return "user/form";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isSameUser(userId)) {
                model.addAttribute("user", user);
                return "user/updateForm";
            }
        }
        return "/users";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable String id, User user, Model model) {
        User beforeUser = users.get(findUser(id));
        if (beforeUser.isSamePassword(user)) {
            beforeUser.changeUserInfo(user);
            return "redirect:/users";
        }
        model.addAttribute("user", beforeUser);
        return "user/updateForm";
    }

    private int findUser(String id) {
        int index = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isSameUser(id)) {
                index = i;
            }
        }
        return index;
    }
}
