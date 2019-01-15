package codesquad;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    List<User> userList = new ArrayList<User>();

    @PostMapping(value="/user/form")
    public String returnForm(Model model, HttpServletRequest request){

        String userId = request.getParameter("userID");
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");

        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        user.setUserName(userName);
        user.setUserEmail(userEmail);

        userList.add(user);

        return "redirect:/users";
    }


/*
    @GetMapping(value="/user/list")
    public String userList(Model model){

        return "user/list";
    }

    @GetMapping(value="/user/{userId}")
    public String userModified(Model model){



        return "user/list";
    }

*/


}
