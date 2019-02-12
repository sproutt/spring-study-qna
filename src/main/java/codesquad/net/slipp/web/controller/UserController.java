package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.service.UserService;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping("")
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @PostMapping("")
    public String createUser(User user, Model model) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {

        return "/user/login";
    }

    @PostMapping("/login")
    public String postLogin(User user, HttpSession session) {
        if (userService.checkIdPassword(user)) {
            session.setAttribute("userSession", user);

            return "redirect:/";
        }

        return "/user/login_failed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userSession");

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));

        return "/user/list_profile";
    }

    @GetMapping("/{id}/form")
    public String getUserUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        User sessionedUser = sessionUtil.getSessionUser(session);
        model.addAttribute("user", userRepository.findById(id).get());

        return "/user/updateForm";
    }

    @GetMapping("/updateForm")
    public String getUpdateForm(HttpSession session) {

        return "redirect:/users/" + sessionUtil.getSessionUser(session).getId() + "/form";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, HttpSession session, User updatedUser, String modifiedPassword) {
        sessionUtil.isSessionMatch(session, userService.findById(id));
        if (!userService.checkIdPassword(updatedUser)) {

            return "/user/error";
        }
        User modelUser = userService.findById(id);
        updatedUser.setPassword(modifiedPassword);
        userService.update(modelUser, updatedUser);

        return "redirect:/users";
    }
}
