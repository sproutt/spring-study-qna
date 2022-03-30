package codesquad.controller;

import codesquad.domain.EditUserDto;
import codesquad.domain.User;
import codesquad.exception.NoSuchUserException;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public String create(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserListForm(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public ModelAndView showUserProfileForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user/profile");
        modelAndView.addObject("user", userRepository.findById(id)
                                                     .orElseThrow(NoSuchUserException::new));
        return modelAndView;
    }

    @GetMapping("/users/{id}/form")
    public String showEditIndividualInfoForm(@PathVariable Long id) {
        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String editIndividualInfo(@PathVariable Long id, EditUserDto editUserDto) {
        User user = userRepository.findById(id)
                                  .orElseThrow(NoSuchUserException::new);
        user.update(editUserDto);
        userRepository.save(user);
        return "redirect:/users";
    }
}
