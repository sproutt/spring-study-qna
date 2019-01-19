package codesquad;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    List<User> users = new ArrayList<User>();


    @GetMapping("/form")
    public String returnForm(){

        return "/users/form";
    }

    @PostMapping("/user/create")
    public String createUser(User user){
        users.add(user);
        return"redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users",users);
        return "/users/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model){
        User user = new User();
        for(int i =0;i<users.size();i++){
            User tmp = users.get(i);
            if(userId.equals(tmp.userId)){
                user = tmp;
            }
        }
        model.addAttribute("user",user);
        return "/users/profile";
    }


}
