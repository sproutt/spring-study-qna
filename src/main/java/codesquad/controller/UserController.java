package codesquad.controller;

import codesquad.model.User;
import codesquad.repository.UserRepository;
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
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }


    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }


    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());

        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "/users/loginForm";
        }

        if (!HttpSessionUtils.getSessionedUser(session).isSameUser(id)) {
            throw new IllegalStateException("wrong access!");
        }

        model.addAttribute("user", userRepository.findById(id).get());

        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getSessionedUser(session);

        if (!sessionedUser.isSameUser(id)) {
            throw new IllegalStateException("wrong access!");
        }

        sessionedUser.update(updatedUser);
        userRepository.save(sessionedUser);

        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            return "/users/login_failed";
        }

        if (!user.isCorrectPassword(password)) {
            return "/users/login_failed";
        }

        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");

        return "redirect:/";
    }
}
