package codesquad.controller;


import codesquad.domain.User;
import codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //유저 목록
    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "/user/list";
    }

    //회원가입 폼
    @GetMapping("/user")
    public String createForm(Model model) {
        model.addAttribute("question", new User());
        return "/user/form";
    }

    //회원가입
    @PostMapping("/user/create")
    public String join(@ModelAttribute User user) {
        System.out.println("UserController.join");
        userService.join(user);
        return "redirect:/users";
    }
}