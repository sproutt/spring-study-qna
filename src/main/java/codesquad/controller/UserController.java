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

    @PostMapping("/users")
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
        model.addAttribute("user", search(userId));
        return "users/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", search(userId));
        return "users/updateForm";
    }

    public User search(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @PostMapping("/users/{userId}")
    public String update(@PathVariable String userId, User modifiedUser, Model model) {
        User user = search(userId);
        long index = user.getId();

        if (!user.getPassword().equals(modifiedUser.getPassword())) {
            model.addAttribute("mismatch", true);
            return "users/updateForm";
        }

        modifiedUser.setId(index);
        users.remove(user);
        users.add((int) index - 1, modifiedUser);
        return "redirect:/users";
    }
}
