package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        System.out.println("user = " + user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("{id}")
    public ModelAndView show(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("user", getUserById(id));
        return mav;
    }

    @GetMapping("{id}/form")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", getUserById(id));
        return "/user/updateForm";
    }

    @PostMapping("{id}/update")
    public String update(User changedUser, @PathVariable("id") Long id, HttpSession session) {
        Object sessionedUser = session.getAttribute("sessionedUser");

        if (sessionedUser != null) {
            User user = (User) sessionedUser;
            if (user.getId().equals(id)) {
                user.update(changedUser);
                userRepository.save(user);
                return "redirect:/users";
            }
        }

        return "/user/login_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User savedUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        if (savedUser.equalsPassword(password)) {
            session.setAttribute("sessionedUser", savedUser);
        }

        return "redirect:/users";
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}