package codesquad.controller;

import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/form")
    public String returnForm() {

        return "/users/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public ModelAndView profile(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("/users/profile");
        mav.addObject("user",userRepository.findById(id).get());
        return mav;
    }

    /*
    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = new User();
        for (int i = 0; i < users.size(); i++) {
            User tmp = users.get(i);
            if (userId.equals(tmp.getUserId())) {
                user = tmp;
            }
        }
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (user.getUserId().equals(users.get(i).getUserId())) {
                users.set(i, user);
            }
        }
        return "redirect:/users";
    }
*/
}
