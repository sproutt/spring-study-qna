package codesquad.controller;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @GetMapping("user/form")
    public String showForm() {
        return "user/form";
    }

    @PostMapping("/users")
    public String create(User user) {
        System.out.println("user : " + user);
        return "redirect:/users";
    }
}
