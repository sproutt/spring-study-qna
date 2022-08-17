package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
class UserController {

    private static final int START_NUMBER = 3;
    private List<User> users = new ArrayList<>();

    @GetMapping("/users/join")
    public String getRegisterForm() {
        return "user/form";
    }

    @PostMapping("/users")
    public String registerUser(@ModelAttribute User user) {
        user.setId(users.size() + START_NUMBER);
        users.add(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {

        User user = findUser(userId);

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());

        return "user/profile";
    }

    private User findUser(String userId) {
        for (User user : users) {
            if (userId.equals(user.getUserId())) {
                return user;
            }
        }
        throw new UserNotFoundException("일치하는 아이디가 없습니다.");
    }
}
