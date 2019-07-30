package codesquad.Controller;

import codesquad.Model.User;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private ArrayList<User> users = new ArrayList<>();

  @PostMapping("/user/signup")
  public String users(User user){

    users.add(user);
    System.out.println("user 추가됨. =>" + users.toString());

    return "redirect:/users";
  }

  @GetMapping("/user/list")
  public String user(Model model){

    model.addAttribute("users", users);
    System.out.println("users 뷰로 전송됨.");

    return "user/list";
  }
}
