package codesquad.controller;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/create")
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
    public ModelAndView profile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("users/profile");
        modelAndView.addObject("user", userRepository
                .findById(id).get());
        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("users/updateForm");
        modelAndView.addObject("user", userRepository
                .findById(id).get());
        return modelAndView;
    }

    @PutMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User user) {
        userRepository.findById(id).get()
                .setName(user.getName());
        userRepository.findById(id).get()
                .setPassword(user.getPassword());
        userRepository.findById(id).get()
                .setEmail(user.getEmail());
        userRepository.save(userRepository.findById(id).get());
        return "redirect:/users";
    }
}
