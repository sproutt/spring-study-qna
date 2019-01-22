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
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);
        System.out.println(user);
        System.out.println("List size : " + users.size());
        return "redirect:/users";
    }


    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                System.out.println("found the user profile");
                model.addAttribute("user", users.get(i));
            }
        }
        return "users/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                model.addAttribute("user", users.get(i));
            }
        }
        return "/users/updateForm";
    }

    @PostMapping("/user/{userId}/update")
    public String updateUser(@PathVariable String userId, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.get(i).setUserId(user.getUserId());
                users.get(i).setName(user.getName());
                users.get(i).setEmail(user.getEmail());
                users.get(i).setPassword(user.getPassword());
                break;
            }
        }
        return "redirect:/users";
    }
}
