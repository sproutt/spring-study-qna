package codesquad.controller;

import codesquad.repository.UserRepository;
import codesquad.domain.User;
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
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/missMatch")
    public String failed_list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/edit_failed_list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            model.addAttribute("user", findUser.get());
            return "user/profile";
        } else {
            return "/users";
        }
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User user = (User) value;
            if (id == user.getId()) {
                model.addAttribute("user", user);
                return "user/updateForm";
            } else {
                return "/users/missMatch";
            }
        } else {
            return "/users";
        }
    }

    @PutMapping("/{id}/update")
    public String editUser(User user, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        User loginUser = (User) value;
        if (loginUser.isSamePassword(user.getPassword())) {
            userRepository.save(loginUser.editProfile(user, loginUser));
            return "redirect:/";
        } else {
            return "user/updateForm";
        }
    }

    @PostMapping("/doLogin")
    public String loginAccess(String userId, String password, HttpSession session) {
        Optional<User> loginUser = userRepository.findByUserId(userId);
        if (loginUser.isPresent()) {
            if (loginUser.get().isSamePassword(password)) {
                session.setAttribute("sessionedUser", loginUser);
                return "redirect:/users";
            } else {
                return "/user/login_failed";
            }
        } else {
            return "redirect:/users";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("seesionedUser");
        session.invalidate();
        return "redirect:/";
    }
}
