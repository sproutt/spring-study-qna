package codesquad.Controller;

import codesquad.Model.User;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private ArrayList<User> users = new ArrayList<>();

  @PostMapping("/user/create")
  public String createUser(User user) {

    users.add(user);
    System.out.println("user 추가됨. =>" + users.toString());

    return "redirect:/user/list";
  }

  @GetMapping("/user/list")
  public String showUserList(Model model) {

    model.addAttribute("users", users);
    System.out.println("users 뷰로 전송됨.");

    return "user/list";
  }

  @GetMapping("/user/profile/{userId}")
  public String showProfile(@PathVariable("userId") String userId, Model model) {
    System.out.println(" userId 전달됨. -> " + userId);

    model.addAttribute("user", findUser(userId));
    System.out.println("user 뷰로 전송됨.");

    return "user/profile";
  }

  private User findUser(String userId) {

    return users.stream().filter((user -> user.getUserId().equals(userId)))
        .findFirst()
        .get();
  }
}
