package codesquad.net.slipp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<User>();

    @PostMapping("/user/create")
    public String welcome(User user, Model model){
        users.add(user);
        System.out.println(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String getList(Model model){
        System.out.println(users);

        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/user/list/{userId}")
    public String getProfile(@PathVariable String userId, Model model){
        System.out.println("검색: " + userId);
        User searchedUser = userSearch(users, userId);
        System.out.println(searchedUser);
        model.addAttribute("user", searchedUser);
        return "/user/list_profile";
    }

    public User userSearch(List<User> users , String userId) {
        User searchedUser = null;
        for (User u : users) {
            System.out.println("비교" + u.getUserId() +"vs" + userId);
            if (u.getUserId().equals(userId)) {
                searchedUser = u;
            }
        }
        return searchedUser;
    }


}
