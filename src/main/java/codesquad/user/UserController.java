package codesquad.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final List<User> registry = new ArrayList<>();

    //유저 목록
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", registry);
        return "user/list";
    }

    //회원가입
    @PostMapping("/users")
    public String join(User user) {
        registry.add(user);
        return "redirect:/users";
    }

    //회원 정보
    @GetMapping("/users/{userId}")
    public String userInfo(Model model, @PathVariable("userId") String userId) {
        model.addAttribute(findUserById(userId));
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(Model model, @PathVariable("id") String id) {
        model.addAttribute(findUserById(id));
        return "/user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String update(User user, @PathVariable("id") String id) {
        User foundUser = findUserById(id);

        if (foundUser.validatePassword(user)) {
            foundUser.update(user);
        }
        return "redirect:/users";
    }

    private User findUserById(String id) {
        return registry.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .get();
    }
}