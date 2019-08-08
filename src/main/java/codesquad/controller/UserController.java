package codesquad.controller;

import codesquad.dto.User;
import codesquad.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  @Resource(name="userService")
  private UserService userService;

  @PostMapping
  public String createUser(User user) {

    userService.addUser(user);

    return "redirect:/users";
  }

  @GetMapping
  public String showUserList(Model model) {

    model.addAttribute("users", userService.getUsers());

    return "user/list";
  }

  @GetMapping("/{userId}")
  public String showProfile(@PathVariable("userId") String userId, Model model) {

    model.addAttribute("user", userService.findUser(userId));

    return "user/profile";
  }

  @GetMapping("/{userId}/form")
  public String showUserInfo(@PathVariable("userId") String userId, Model model) {

    model.addAttribute("user", userService.findUser(userId));

    return "user/updateForm";
  }

  @PutMapping("/{userId}")
  public String updateUser(@PathVariable("userId") String userId, User user) {
    System.out.println("put   실행됨.");
    return userService.updateUserUrl(userId, user);
  }
}
