package codesquad.controller;

import codesquad.domain.User;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "/user/list";
    }

    @PostMapping("")
    public String signUp(User user) {
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping("/form")
    public String showForm() {
        return "/user/form";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId,Model model){
        model.addAttribute("user",userService.getUser(userId));
        return "/user/profile";
    }

}
