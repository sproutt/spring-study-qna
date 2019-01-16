package codesquad.net.slipp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {
    private List<User> users = new ArrayList<User>();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/user/create")
    public String welcome(User user, Model model){
        users.add(user);
        logger.info(user.toString());

        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String getList(Model model){
        //logger.info(users.toString());

        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/user/list/{userId}")
    public String getProfile(@PathVariable String userId, Model model){
        logger.info("검색: " + userId);
        User searchedUser = userSearch(users, userId);
        logger.info(searchedUser.toString());
        model.addAttribute("user", searchedUser);
        return "/user/list_profile";
    }

    public User userSearch(List<User> users , String userId) {
        User searchedUser = null;
        for (User u : users) {
            logger.info("비교" + u.getUserId() +"vs" + userId);
            if (u.getUserId().equals(userId)) {
                searchedUser = u;
            }
        }
        return searchedUser;
    }


}
