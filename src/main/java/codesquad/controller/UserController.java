package codesquad.controller;

import codesquad.domain.EditUserDto;
import codesquad.domain.User;
import codesquad.exception.InvalidPasswordException;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

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
                                                     .orElseThrow(NoSuchElementException::new));
        return modelAndView;
    }

    @GetMapping("/users/{id}/form")
    public String showEditIndividualInfoForm(@PathVariable Long id, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
            User sessionedUser = (User) sessionValue;
            try {
                userRepository.findById(id)
                              .filter(s -> s.getId()
                                            .equals(sessionedUser.getId()))
                              .orElseThrow(NoSuchElementException::new);
            } catch (NoSuchElementException exception) {
                return "user/edit_failed";
            }
            return "user/update_form";
        }
        return "user/no_session_form";
    }

    @PutMapping("/users/{id}")
    public String editIndividualInfo(@PathVariable Long id, EditUserDto editUserDto, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
            try {
                User user = userRepository.findById(id)
                                          .orElseThrow(NoSuchElementException::new);
                user.update(editUserDto);
                userRepository.save(user);
            } catch (InvalidPasswordException exception) {
                return "user/invailid_password_form";
            } catch (NoSuchElementException exception) {
                return "user/no_user_form";
            }
            return "redirect:/users";
        }
        return "user/no_session_form";
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
                                  .orElseThrow(NoSuchElementException::new);
        session.setAttribute("sessionedUser", user);
        return "index";
    }

    private Object getSessionValue(HttpSession session) {
        return session.getAttribute("sessionedUser");
    }
}
