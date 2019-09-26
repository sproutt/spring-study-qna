package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<User>();

    @PostMapping("/users")
    public String create(User user) {
        System.out.println("user : " + user);
        user.setUserIndex(users.size() + 1);
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getProfile(@PathVariable("userId") String userId, Model model) {

        for (User user : users) {
            if (user.isSameUser(userId, user)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.isSameUser(userId, user)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String editUser(@PathVariable("userId") String userId, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                if (users.get(i).getPassword().equals(user.getPassword())) {
                    users.remove(i);
                    users.add(i, user);
                    users.get(i).setPassword(user.getChangePassword());
                    users.get(i).setUserIndex(i + 1);
                    break;
                } else {
                    return "user/updateForm";
                }
            }
        }
        return "redirect:/users";
    }
}
