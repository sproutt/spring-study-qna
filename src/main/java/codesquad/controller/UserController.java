package codesquad.controller;

import codesquad.domain.User;
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
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, HttpSession httpSession, Model model) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        if (!user.match(id)) {
            return "users/update_deny";
        }

        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User modifiedUser, Model model) {
        User user = userService.findById(id);

        if (!user.match(modifiedUser.getPassword())) {
            model.addAttribute("mismatch", true);
            return "users/updateForm";
        }

        userService.update(user, modifiedUser);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession, Model model) {
        User user = userService.findByUserId(userId);

        if (!user.match(password)) {
            model.addAttribute("mismatch", true);
            return "users/login";
        }
        httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
