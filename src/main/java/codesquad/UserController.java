package codesquad;

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
    public ModelAndView show(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateForm(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("user/updateForm");
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @PostMapping("/{id}/update")
    public String editUser(@PathVariable("id") Long id, User user) {
        User afterUser = userRepository.findById(id).get();
        if (afterUser.getPassword().equals(user.getPassword())) {
            afterUser.setName(user.getName());
            afterUser.setEmail(user.getEmail());
            afterUser.setPassword(user.getChangePassword());
            userRepository.save(afterUser);
        } else {
            return "user/updateForm";
        }
        return "redirect:/users";
        /*
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                if (users.get(i).getPassword().equals(user.getPassword())) {
                    users.remove(i);
                    users.add(i, user);
                    users.get(i).setPassword(user.getChangePassword());
                    users.get(i).setUserIndex(i + 1);
                    break;
                } else {
                    return "user/updateForm";
                }
            }
        }
        */
        //return "redirect:/users";
    }

}
