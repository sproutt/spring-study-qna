package codesquad.controller;

import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/form")
    public String returnForm() {

        return "/users/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String profile(Model model, @PathVariable long id) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/users/profile";
    }


    @GetMapping("/loginForm")
    public String loginForm() {
        return "/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/loginForm";
        }
        if (!user.getPassword().equals(password)) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute("user", user);
        return "redirect:/";
    }


    @GetMapping("/updateForm")
    public String updateForm() {
        return "/users/updateForm";
    }

    @PostMapping("/update/{id}")
    public String updateUser(User newUser, @PathVariable long id) {
        User user = userRepository.findById(id).get();
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/users/logout";
    }

}
