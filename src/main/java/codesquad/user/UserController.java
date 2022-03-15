package codesquad.user;


import codesquad.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class UserController {

    private final ArrayList<User> registry = new ArrayList<>();

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
        for (User user : registry) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute(user);
                break;
            }
        }
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(Model model, @PathVariable("id") String id) {
        for (User user : registry) {
            if(user.getUserId().equals(id)) {
                model.addAttribute(user);
                break;
            }
        }

        return "/user/updateForm";
    }
}