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
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/users/form")
    public String signUp() {
        return "user/form";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getProfile(@PathVariable("userId") String userId, Model model) {
        //System.out.println(users.toString());
        /*
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
               model.addAttribute("user", users.get(i)); //정상적으로 작동
               break;
            }
        }*/

        for (User user : users) {
            if (user.isSameUser(userId, user)) {
                model.addAttribute("user", user);
                break;
            }
        }

        return "profile";
    }
}
