package codesquad.controller;

import codesquad.dto.User;
import codesquad.service.UserService;
import codesquad.util.HttpSessionUtils;
import javax.servlet.http.HttpSession;
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

  private UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
    return "redirect:/";
  }

  @PostMapping("/login")
  public String login(String userId, String password, HttpSession session) {

    if (!userService.isUserFromDB(userId, password)) {
      return "redirect:/users/login/form";
    }

    session.setAttribute("sessionedUser", userService.getUserByUserId(userId));

    return "redirect:/";
  }

  @PostMapping
  public String createUser(User user) {

    userService.addUser(user);

    return "redirect:/users";
  }

  @GetMapping
  public String showUsers(Model model) {

    model.addAttribute("users", userService.getUsers());

    return "user/list";
  }

  @GetMapping("/{id}")
  public String showProfile(@PathVariable("id") Long id, Model model) {

    model.addAttribute("user", userService.findUserById(id));

    return "user/profile";
  }

  @GetMapping("/{id}/form")
  public String showUserInfo(@PathVariable("id") Long id, Model model, HttpSession session) {

    if (HttpSessionUtils.isLoginUser(session)) {
      return "redirect:/users/login/form";
    }

    User sessionedUser = HttpSessionUtils.getUserFromSession(session);

    if (!sessionedUser.isSameId(id)) {
      throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
    }

    model.addAttribute("user", userService.findUserById(id));

    return "user/updateForm";
  }

  @PutMapping("/{id}")
  public String updateUser(@PathVariable("id") Long id, User updatedUser, HttpSession session) {

    if (HttpSessionUtils.isLoginUser(session)) {
      return "redirect:/users/login/form";
    }

    User sessionedUser = HttpSessionUtils.getUserFromSession(session);

    if (!sessionedUser.isSameId(id)) {
      throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
    }

    return userService.updateUser(id, updatedUser);
  }
}
