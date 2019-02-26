package codesquad.controller;

import codesquad.model.User;
import codesquad.service.UserService;
import codesquad.utils.HttpSessionUtils;
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
        userService.saveUser(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findUsers());

        return "/users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));

        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.isSameUserSessioned(id, HttpSessionUtils.getSessionedUser(session))) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("user", userService.findById(id));

        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, String password, HttpSession session) {
        if (!userService.isSameUserSessioned(id, HttpSessionUtils.getSessionedUser(session))) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User) HttpSessionUtils.getSessionedUser(session);
        if (!sessionedUser.isCorrectPassword(password)) {
            return "redirect:/users/{id}/form";
        }

        sessionedUser.update(updatedUser);
        userService.saveUser(sessionedUser);

        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);

        if (!user.isCorrectPassword(password)) {
            return "/users/login_failed";
        }
        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }
}
