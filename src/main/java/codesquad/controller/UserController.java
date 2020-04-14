package codesquad.controller;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userRepository.selectAll());
        return "/user/list";
    }

    @PostMapping("")
    public String signUp(User user) {
        userRepository.insert(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.select(userId));
        return "/user/profile";
    }

}
