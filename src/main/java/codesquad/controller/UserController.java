package codesquad.controller;

import codesquad.domain.User;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public String create(User user) {
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        return userService.update(id, model,session);
    }

    @PutMapping("/{id}/update")
    public String editUser(@PathVariable("id") Long id, User user, HttpSession session) {
        return  userService.userEdit(id, user, session);
    }

    @PostMapping("/doLogin")
    public String loginAccess(String userId, String password, HttpSession session) {
        return userService.login(userId,password,session);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session);
        return "redirect:/";
    }
}
