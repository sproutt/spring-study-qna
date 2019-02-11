package codesquad.controller;

import codesquad.model.User;
import codesquad.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private SessionChecker sessionChecker;

    @Autowired
    private OptionalProcessor optionalProcessor;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = optionalProcessor.getUserByUserId(userId);
        session.setAttribute(USER_SESSION, user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION);
        return "redirect:/users ";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", optionalProcessor.getUserById(id));
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = optionalProcessor.getUserById(id);
        User sessionedUser = sessionChecker.loggedinUser(session);
        if (!user.isSameUser(sessionedUser)) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User user) {
        User originalUser = optionalProcessor.getUserById(id);
        originalUser.update(user);
        userRepository.save(originalUser);
        return "redirect:/users";
    }
}
