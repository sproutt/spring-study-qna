package codesquad.web;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.util.HttpSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/form")
    public String userForm() {
        return "user/form";
    }

    @GetMapping("/users/login/form")
    public String userLoginForm() {
        return "user/login";
    }

    @PostMapping("/users")
    public String create(User user) {
        log.info("user = {}", user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public ModelAndView profile(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        User savedUser = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        mav.addObject("user", savedUser);
        return mav;
    }

    @GetMapping("/users/{id}/form")
    public String userInfoUpdateForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

        if(!sessionedUser.equalsId(id)) {
            return "failed";
        }

        model.addAttribute("user", sessionedUser);

        return "user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable Long id, User updatedUser, HttpSession httpSession) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

        if(!sessionedUser.equalsId(id)) {
            return "failed";
        }

        if (!sessionedUser.equalsPassword(updatedUser.getPassword())) {
            return "failed";
        }

        sessionedUser.update(updatedUser);
        userRepository.save(sessionedUser);

        return "redirect:/users";
    }


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession) {
        User savedUser = userRepository.findByUserId(userId);

        if(savedUser == null) {
            return ":redirect/users/login/form";
        }

        if(!savedUser.equalsPassword(password)) {
            return ":redirect/users/login/form";
        }

        httpSession.setAttribute("sessionedUser", savedUser);
        return "redirect:/";
    }
}
