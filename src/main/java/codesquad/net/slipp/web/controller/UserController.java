package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<User>();

    @Autowired
    private UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public String getUserList(Model model) {
        log.info(userRepository.findAll().toString());
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String createUser(User user, Model model) {
        log.info(user.toString());
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/list")
    public String getList() {
        return "redirect:/users";
    }


    @GetMapping("/{id}")
    public ModelAndView getUserProfile(@PathVariable long id, Model model) {
        ModelAndView mav = new ModelAndView("user/list_profile");
        mav.addObject("user", userRepository.findById(id).get());
        return mav;
    }

    @RequestMapping("/users/{id}/form")
    public String getUserUpdateForm(@PathVariable int id, Model model, @RequestParam(required = false, name = "msg") String msg) {

        model.addAttribute("user", users.get(id));
        model.addAttribute("id", id);
        model.addAttribute("msg", msg);
        return "/user/updateForm";
    }

    @RequestMapping(value = "/users/{id}/update", method = RequestMethod.POST)
    public String updateUser(@PathVariable int id, User updatedUser, RedirectAttributes redirectAttributes) {

        if (isSame(updatedUser.getPassword(), users.get(id).getPassword())) {
            //비밀번호 맞을시 업데이트
            users.set(id, updatedUser);
            return "redirect:/users";
        }
        redirectAttributes.addAttribute("msg", "잘못된 비밀번호입니다.");
        // 비밀번호 틀릴시, 안내문구 전송해서,updateform 다시띄운후에 안내문구 출력.

        return "redirect:/users/" + id + "/form";
    }


    public boolean isSame(String a, String b) {
        return a.equals(b);
    }


}
