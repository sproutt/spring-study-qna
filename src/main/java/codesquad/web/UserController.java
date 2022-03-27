package codesquad.web;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public String create(User user) {
        System.out.println("user = " + user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isSameUser(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String userInfoUpdateForm(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.isSameUser(userId)) {
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
                              .findFirst().orElseThrow(RuntimeException::new);

        if (savedUser.equalsPassword(updatedUser.getPassword())) {
            savedUser.update(updatedUser);
        }

        return "redirect:/users";

    }
}
