package codesquad.controller;

import codesquad.domain.User;
import codesquad.service.UserService;
import codesquad.util.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {

    HttpSessionUtils.removeUserFromSession(session);

    return "redirect:/";
  }

  @PostMapping("/login")
  public String login(String userId, String password, HttpSession session) {

    userService.checkUserFromDB(userId, password);
    HttpSessionUtils.setUserFromSession(session, userService.findUserByUserId(userId));

    return "redirect:/";
  }

  @PostMapping
  public String createUser(User user) {

    userService.addUser(user);

    return "redirect:/users";
  }

  @GetMapping
  public String showUsers(Model model) {

    model.addAttribute("users", userService.findAllUsers());

    return "user/list";
  }

  @GetMapping("/{id}")
  public String showProfile(@PathVariable("id") Long id, Model model) {

    model.addAttribute("user", userService.findUserById(id));

    return "user/profile";
  }

  @GetMapping("/{id}/form")
  public String showUserInfo(@PathVariable("id") Long id, HttpSession session) {

    HttpSessionUtils.checkLogining(session);
    userService.checkIdOfSession(HttpSessionUtils.getUserFromSession(session), id);

    return "user/updateForm";
  }

  @PutMapping("/{id}")
  public String updateUser(@PathVariable("id") Long id, User updatedUser, HttpSession session) {

    HttpSessionUtils.checkLogining(session);
    userService.checkIdOfSession(HttpSessionUtils.getUserFromSession(session), id);

    return userService.updateUserService(id, updatedUser);
  }
}
