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
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        userRepository.findById(id).orElseThrow(NullPointerException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User user = (User) value;
            if (id.equals(user.getId())) {
                model.addAttribute("user", user);
                return "user/updateForm";
            } else {
                model.addAttribute("userMissMatch", userRepository.findAll());
                return "user/list";
            }
        } else {
            return "user/login";
        }
    }

    @PutMapping("/{id}/update")
    public String editUser(@PathVariable("id") Long id, User user, HttpSession session) {
        userRepository.findById(id).orElseThrow(NullPointerException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User originUser = (User) value;
            if (originUser.isSamePassword(user.getPassword())) {
                originUser.editProfile(user);
                userRepository.save(originUser);
                return "redirect:/users";
            } else {
                return "user/updateForm";
            }
        } else {
            return "user/login";
        }
    }

    @PostMapping("/doLogin")
    public String loginAccess(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        User user = maybeUser.orElseThrow(NullPointerException::new);
        if (user.isSamePassword(password)) {
            session.setAttribute("sessionedUser", user);
            return "redirect:/users";
        } else {
            return "user/login_failed";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("seesionedUser");
        session.invalidate();
        return "redirect:/";
    }
}
