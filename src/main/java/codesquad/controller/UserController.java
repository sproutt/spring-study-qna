package codesquad.controller;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users";
        }
        if (!password.equals(user.getPassword())) {
            return "redirect:/users";
        }
        session.setAttribute("sessionedUser", user);
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/users ";
    }

    @PostMapping("/create")
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
        model.addAttribute("user", userRepository.findById(id).get());
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Object sessionedObject = session.getAttribute("sessionedUser");
        if (sessionedObject == null) {
            return "redirect:/users/login";
        }
        User sessionedUser = (User) sessionedObject;
        model.addAttribute("user", sessionedUser);
        return "users/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User user, HttpSession session) {
        Object sessionedObject = session.getAttribute("sessionedUser");
        if (sessionedObject == null) {
            return "redirect:/users/login";
        }
        User sessionedUser = (User) sessionedObject;
        User newUser = userRepository.findById(sessionedUser.getId()).get();
        newUser.update(user);
        userRepository.save(userRepository.findById(id).get());
        return "redirect:/users";
    }
}
