package codesquad.web;

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
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String userInfoUpdateForm(@PathVariable String userId, Model model) {
        for (User user : users) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, Model model, User updatedUser) {
        User savedUser = users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .get();

        if(savedUser.validatePassword(updatedUser)) {
            savedUser.update(updatedUser);
        }

        return "redirect:/users";
    }
}
