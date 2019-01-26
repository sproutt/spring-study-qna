package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private List<User> users = new ArrayList<User>();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/user/create")
    public String welcome(User user, Model model) {
        users.add(user);
        logger.info(user.toString());

        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String getList() {
        return "redirect:/users";
    }

    @RequestMapping("/users")
    public String getUsers(Model model) {
        logger.info(users.toString());

        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/user/list/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        logger.info("검색: " + userId);
        User searchedUser = userSearch(users, userId);
        logger.info(searchedUser.toString());
        model.addAttribute("user", searchedUser);
        return "/user/list_profile";
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

    public User userSearch(List<User> users, String userId) {
        User searchedUser = null;
        for (User user : users) {
            logger.info("비교" + user.getUserId() + "vs" + userId);
            if (user.getUserId().equals(userId)) {
                searchedUser = user;
            }
        }
        return searchedUser;
    }

    public boolean isSame(String a, String b) {
        return a.equals(b);
    }


}
