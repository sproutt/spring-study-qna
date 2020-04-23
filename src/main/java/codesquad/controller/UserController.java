package codesquad.controller;

import codesquad.service.UserService;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {
    private final static Logger LOGGER = Logger.getGlobal();

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String create(String userId, String password, String name, String email) {
        userService.create(userId, password, name, email);
        return "redirect:/users";
    }

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "user/profile";
    }

    @GetMapping("/form")
    public String show() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("user", userService.getLoginUser(id, session));
        return "user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, String password, String changedPassword, String name, String email, Model model, HttpSession session) {
        if (!userService.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }
        if (userService.checkPassword(password, session)) {
            userService.updateUser(changedPassword, name, email, session);
            return "redirect:/users";
        }
        model.addAttribute("user", userService.getLoginUser(id, session));
        return "user/updateForm";
    }

    //로그인 기능
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        if (userService.findUser(userId) == null) {
            return "redirect:/users/loginForm";
        }
        if (!userService.checkPassword(userId, password)) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, userService.findUser(userId));
        LOGGER.info("Login success");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        LOGGER.info("Logout success");
        return "redirect:/";
    }
}
