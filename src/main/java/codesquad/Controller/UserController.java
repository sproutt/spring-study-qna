package codesquad.Controller;

import codesquad.Model.User;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  ArrayList<User> users = new ArrayList<>();

  @PostMapping("/users")
  public String users(User user){

    users.add(user);

    return "redirect:/users";
  }

  @GetMapping("/users")
  public String user(){

    return "userlist";
  }
}
