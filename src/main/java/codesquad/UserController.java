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

    @GetMapping("/")
    public String returnIndex(){

        return "/index";
    }


    @GetMapping("/user/form")
    public String returnForm(){

        return "/user/form";
    }




}
