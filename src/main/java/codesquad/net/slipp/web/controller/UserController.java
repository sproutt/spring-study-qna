package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.User;
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
    private UserService userService;

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String create(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        if (userService.checkIdPassword(userId, password)) {
            session.setAttribute("userSession", userService.findByUserId(userId));

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
        model.addAttribute("user", userService.findById(id));

        return "/user/list_profile";
    }

    @GetMapping("/{id}/form")
    public String userUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        model.addAttribute("user", SessionUtil.getAuthUser(session, userService.findById(id)));

        return "/user/updateForm";
    }

    public String updateForm(HttpSession session) {
        return new StringBuffer("redirect:/users/").
                append(SessionUtil.getSessionUserId(session)).
                append("/form").toString();
}

    @PutMapping("/{id}")
    public String update(@PathVariable long id, HttpSession session, User updatedUser, String modifiedPassword) {
        if (!userService.checkIdPassword(updatedUser)) {
            return "/user/error";
        }
        userService.update(SessionUtil.getAuthUser(session, userService.findById(id)),
                updatedUser, modifiedPassword);

        return "redirect:/users";
    }
}
