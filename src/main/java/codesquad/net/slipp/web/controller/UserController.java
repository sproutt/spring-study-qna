package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.service.UserService;
import codesquad.net.slipp.web.utils.ErrorMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("")
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @PostMapping("")
    public String createUser(User user, Model model) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {

        return "/user/login";
    }

    @PostMapping("/login")
    public String postLogin(User user, HttpSession session) {
        if (userService.checkLogin(user)) {
            session.setAttribute("userSession", user);

            return "redirect:/";
        }

        return "/user/login_failed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userSession");

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView getUserProfile(@PathVariable long id, Model model) {
        ModelAndView mav = new ModelAndView("/user/list_profile");
        mav.addObject("user", userRepository.findById(id).get());

        return mav;
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUserUpdateForm(@PathVariable long id, Model model) {
        ModelAndView mav = new ModelAndView("/user/updateForm");
        mav.addObject("user", userRepository.findById(id).get());

        return mav;
    }

    @GetMapping("/updateForm")
    public String getUpdateForm(HttpSession session) {
        Object value = session.getAttribute("userSession");
        if (value == null) {

            return "redirect:/users/login";
        }
        User sessionedUser = (User) value;

        return "redirect:/users/" + sessionedUser.getId() + "/form";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, HttpSession session, User updatedUser, String modifiedPassword) {
        User user = userRepository.findById(id).get();
        Object value = session.getAttribute("userSession");
        User sessionedUser;
        if (value == null) {

            return "redirect:/users/login";
        }
        sessionedUser = (User) value;
        if (!isSame(sessionedUser.getUserId(), updatedUser.getUserId())) {

            return "redirect:/users/" + id + "/form";
        }
        if (isSame(updatedUser.getPassword(), user.getPassword())) {
            user.setPassword(modifiedPassword);
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            userRepository.save(user);

            return "redirect:/users";
        }

        return "redirect:/users/" + id + "/form";
    }

    public boolean isSame(String a, String b) {

        return a.equals(b);
    }

}
