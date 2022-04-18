package codesquad.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public String create(User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        userService.list(model);
        return "/user/list";
    }

    @GetMapping("{id}")
    public ModelAndView showUser(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        userService.show(id, mav);
        return mav;
    }

    @GetMapping("{id}/form")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        userService.setUpdateForm(model, id);
        return "/user/updateForm";
    }

    @PostMapping("{id}/update")
    public String updateUser(User changedUser, @PathVariable("id") Long id, HttpSession session) {

        if (userService.update(changedUser, id, session)) {
            return "redirect:/users";
        }

        return "/user/login_failed";
    }

    @PostMapping("/login")
    public String loginUser(UserDto userDto, HttpSession session) {
        userService.login(userDto, session);

        return "redirect:/users";
    }
}