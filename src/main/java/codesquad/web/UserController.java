package codesquad.web;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public String create(User user) {
        System.out.println("user = " + user);
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
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @GetMapping("/users/{id}/form")
    public String userInfoUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable Long id, Model model, User updatedUser) {
        User savedUser = userRepository.findById(id).orElseThrow(RuntimeException::new);

        if (savedUser.equalsPassword(updatedUser.getPassword())) {
            savedUser.update(updatedUser);
            userRepository.save(savedUser);
        }

        return "redirect:/users";
    }
}
