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
    public String join(SingUpUserDto singUpUserDto) {
        userService.create(singUpUserDto);
        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.list());
        return "/user/list";
    }

    @GetMapping("{id}")
    public ModelAndView showUser(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        mav.addObject("user", userService.findUserById(id));
        return mav;
    }

    @GetMapping("{id}/form")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "/user/updateForm";
    }

    @PostMapping("{id}/update")
    public String updateUser(SingUpUserDto changedUser, @PathVariable("id") Long id, HttpSession session) {

        if (userService.update(changedUser, id, session)) {
            return "redirect:/users";
        }

        return "/user/login_failed";
    }

    @PostMapping("/login")
    public String loginUser(LoginUserDto loginUserDto, HttpSession session) {
        if(userService.login(loginUserDto, session)){
            return "redirect:/users";
        }

        return "/user/login_failed";
    }
}