package codesquad.controller;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String signUp(User user) {
        userRepository.insert(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.find(userId));
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.find(userId));
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable String userId, String currentPassword, String newPassword, String name, String email) {
        User user = userRepository.find(userId);
        if (user.getPassword() == Integer.parseInt(currentPassword)) {
            user.update(Integer.parseInt(newPassword), name, email);
            return "redirect:/users";
        }
        return "redirect:/users/" + userId + "/form";
    }
}
