package codesquad.controller;

import codesquad.UserRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/missMatch")
    public String failed_list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/edit_failed_list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            model.addAttribute("user", findUser.get());
            return "user/profile";
        } else {
            return "/users";
        }
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User user = (User) value;
            if (id == user.getId()) {
                model.addAttribute("user", user);
                return "user/updateForm";
            } else {
                return "/users/missMatch";
            }
        } else {
            return "/users";
        }
    }

    @PostMapping("/{id}/update")
    public String editUser(/*@PathVariable("id") Long id,*/ User user, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        User loginUser = (User) value;
        if (loginUser.isSamePassword(user, loginUser)) {
            userRepository.save(loginUser.editProfile(user, loginUser));
            return "redirect:/";
        } else {
            return "user/updateForm";
        }
    }

    @PostMapping("/userLogin")
    public String loginAccess(String userId, String password, HttpSession session) {
        User loginUser = userRepository.findByUserId(userId);
        if (loginUser.isSamePasswordForLogin(password, loginUser)) {
            session.setAttribute("sessionedUser", loginUser);
        } else {
            return "/user/login_failed";
        }
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("seesionedUser");
        session.invalidate();
        return "redirect:/";
    }
}
