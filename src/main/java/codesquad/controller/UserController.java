package codesquad.controller;

import codesquad.domain.EditUserDto;
import codesquad.domain.User;
import codesquad.exception.NoSuchUserException;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
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
    public String showEditIndividualInfoForm(@PathVariable Long id, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User sessionedUser = (User) value;
            try {
                userRepository.findById(id)
                              .filter(s -> s.getId()
                                            .equals(sessionedUser.getId()))
                              .orElseThrow(NoSuchUserException::new);
            } catch (NoSuchUserException exception) {
                return "user/edit_failed";
            }
        }
        return "user/update_form";
    }

    @PutMapping("/users/{id}")
    public String editIndividualInfo(@PathVariable Long id, EditUserDto editUserDto) {
        try {
            User user = userRepository.findById(id)
                                      .orElseThrow(NoSuchUserException::new);
            user.update(editUserDto);
            userRepository.save(user);
        } catch (NoSuchUserException exception) {
            return "user/no_user_form";
        }
        return "redirect:/users";
    }

    @GetMapping("/users/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId)
                                  .filter(s -> s.getPassword()
                                                .equals(password))
                                  .orElseThrow(NoSuchUserException::new);
        session.setAttribute("sessionedUser", user);
        return "index";
    }
}
