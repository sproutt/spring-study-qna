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
    public ModelAndView profile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("users/profile");
        modelAndView.addObject("user", userRepository.findById(id).get());

        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public String modifyProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());

        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, User updatedUser) {
        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);

        return "redirect:/users";
    }
}
