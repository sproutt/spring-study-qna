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

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @PostMapping("")
    public String create(User user, Model model) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {

        return "/user/login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
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
    public String userProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));

        return "/user/list_profile";
    }

    @GetMapping("/{id}/form")
    public String userUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionUser(session);
        model.addAttribute("user", userService.findById(id));

        return "/user/updateForm";
    }

    @GetMapping("/updateForm")
    public String updateForm(HttpSession session) {

        return "redirect:/users/" + SessionUtil.getSessionUser(session).getId() + "/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, HttpSession session, User updatedUser, String modifiedPassword) {
        SessionUtil.isSessionMatch(session, userService.findById(id));
        if (!userService.checkIdPassword(updatedUser)) {

            return "/user/error";
        }
        User modelUser = userService.findById(id);
        updatedUser.setPassword(modifiedPassword);
        userService.update(modelUser, updatedUser);

        return "redirect:/users";
    }
}
