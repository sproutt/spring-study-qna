package codesquad.controller;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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
    public String get(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("/form")
    public String show() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, User user, Model model) {
        User beforeUser = userRepository.findById(id).get();
        if (beforeUser.isSamePassword(user)) {
            beforeUser.changeUserInfo(user);
            userRepository.save(beforeUser);
            return "redirect:/users";
        }
        model.addAttribute("user", beforeUser);
        return "user/updateForm";
    }
}
