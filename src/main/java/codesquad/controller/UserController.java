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
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            model.addAttribute("user", findUser.get());
            return "user/updateForm";
        } else {
            return "/users";
        }
    }

    //setter사용금지 아직 미반영
    @PostMapping("/{id}/update")
    public String editUser(@PathVariable("id") Long id, User user) {
        User changedUser = userRepository.findById(id).get();
        if (changedUser.isSamePasswordForEdit(changedUser, user)) {
            //user.changeUserInfo(changedUser,user);

            changedUser.setName(user.getName());
            changedUser.setEmail(user.getEmail());
            changedUser.setPassword(user.getChangePassword());

            userRepository.save(changedUser);
        } else {
            return "user/updateForm";
        }
        return "redirect:/users";
    }

    @PostMapping("/userLogin")
    public String login(String userId, String password, HttpSession session) {
        User loginUser = userRepository.findByUserId(userId);
        if (loginUser.isSamePasswordForLogin(password, loginUser)) {
            session.setAttribute("sessionedUser", loginUser);
        } else {
            return "/user/login_failed";
        }
        return "redirect:/users";
    }
}
