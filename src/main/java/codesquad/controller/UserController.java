package codesquad.controller;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.utils.HttpSessionUtils;
import codesquad.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        Optional<User> byId = userRepository.findById(id);
        User user = byId.orElseThrow(() -> new UserNotFoundException(id));

        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession httpSession, Model model) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/invalid";
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
        Optional<User> byId = userRepository.findById(id);
        User user = byId.orElseThrow(() -> new UserNotFoundException(id));

        if (!user.match(modifiedUser.getPassword())) {
            model.addAttribute("mismatch", true);
            return "users/updateForm";
        }
        user.update(modifiedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession, Model model) {
        Optional<User> byUserId = userRepository.findByUserId(userId);
        User user = byUserId.orElseThrow(() -> new UserNotFoundException(userId));

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
