package codesquad.controller;

import codesquad.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    List<User> users = new ArrayList<User>();


    @GetMapping("/form")
    public String returnForm() {

        return "/users/form";
    }

    @PostMapping("/users/create")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = new User();
        for (int i = 0; i < users.size(); i++) {
            User tmp = users.get(i);
            if (userId.equals(tmp.getUserId())) {
                user = tmp;
            }
        }
        model.addAttribute("user", user);
        return "/users/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = new User();
        for (int i = 0; i < users.size(); i++) {
            User tmp = users.get(i);
            if (userId.equals(tmp.getUserId())) {
                user = tmp;
            }
        }
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @GetMapping("/users/{userId}/update")
    public String updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (user.getUserId().equals(users.get(i).getUserId())) {
                users.set(i, user);
            }
        }
        return "redirect:/users";
    }

}
