package codesquad.controller;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            model.addAttribute("user", maybeUser.get());
            return "user/profile";
        }
        return "/users";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            Optional<User> maybeUser = (Optional<User>) value;
            if (id.equals(maybeUser.get().getId())) {
                model.addAttribute("user", maybeUser.get());
                return "user/updateForm";
            } else {
                model.addAttribute("userMissMatch", userRepository.findAll());
                return "user/list";
            }
        } else {
            return "/users";
        }
    }

    @PutMapping("/{id}/update")
        public String editUser(User user, HttpSession session) {
            Object value = session.getAttribute("sessionedUser");
            Optional<User> maybeUser = (Optional<User>) value;
            if (maybeUser.get().isSamePassword(user.getPassword())) {
                maybeUser.get().editProfile(user);
                userRepository.save(maybeUser.get());
                return "redirect:/users";
            } else {
                return "user/updateForm";
            }
        }

    @PostMapping("/doLogin")
    public String loginAccess(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (maybeUser.isPresent()) {
            if (maybeUser.get().isSamePassword(password)) {
                session.setAttribute("sessionedUser", maybeUser);
                return "redirect:/users";
            } else {
                return "user/login_failed";
            }
        } else {
            return "/users";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("seesionedUser");
        session.invalidate();
        return "redirect:/";
    }
}
