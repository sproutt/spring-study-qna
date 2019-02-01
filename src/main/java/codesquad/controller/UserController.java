package codesquad.controller;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        model.addAttribute("user", userRepository.findById(id).get());
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession httpSession, Model model) {
        Object value = httpSession.getAttribute("sessionedUser");
        if (value == null) {
            return "users/invalid";
        }
        User user = (User) value;

        if (!user.match(id)) {
            return "users/update_deny";
        }
        model.addAttribute("user", userRepository.findById(id).get());
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User modifiedUser, Model model) {
        User user = userRepository.findById(id).get();

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
        User user = userRepository.findByUserId(userId);

        if (user == null || !user.match(password)) {
            model.addAttribute("mismatch", true);
            return "users/login";
        }
        httpSession.setAttribute("sessionedUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("sessionedUser");
        return "redirect:/";
    }
}
