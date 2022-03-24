package codesquad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final List<User> registry = new ArrayList<>();

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
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @GetMapping("{id}/form")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PostMapping("{id}/update")
    public String update(User user, @PathVariable("id") Long id) {
        User foundUser = userRepository.findById(id).get();

        if (foundUser.validatePassword(user)) {
            foundUser.update(user);
        }
        userRepository.save(foundUser);
        return "redirect:/users";
    }
}