package codesquad.controller;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import codesquad.service.UserService;
import codesquad.utils.OptionalProcessor;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String USER_SESSION = "sessionedUser";

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.getUserByUserId(userId);
        session.setAttribute(USER_SESSION, user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION);
        return "redirect:/users";
    }

    @PostMapping
    public String create(User user) {
        userService.save(user);
        return "redirect:/users/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User userToUpdate = userService.getUserById(id);
        User sessionedUser = (User) session.getAttribute(USER_SESSION);
        if (!sessionedUser.isSameUser(sessionedUser)) {
            return "redirect:/users";
        }
        model.addAttribute("user", userToUpdate);
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User user) {
        userService.update(user, id);
        return "redirect:/users";
    }
}
