package codesquad.controller;

import codesquad.model.user.User;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String returnList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String goToProfile(Model model, @PathVariable Long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/users/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String userId, HttpSession session) {
        User user = userService.findByUserId(userId);
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateUser(User newUser, @PathVariable Long id) {
        userService.update(id, newUser);
        return "redirect:/users/logout";
    }

}
