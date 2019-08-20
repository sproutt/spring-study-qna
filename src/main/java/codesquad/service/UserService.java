package codesquad.service;

import codesquad.dao.UserRepository;
import codesquad.dto.User;
import codesquad.util.HttpSessionUtils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void addUser(User user) {
    userRepository.save(user);
  }

  public User findUserById(Long Id) {
    return userRepository.findById(Id).get();
  }

  public List<User> findAllUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);

    return users;
  }

  public boolean isUserFromDB(String userId, String password) {
    User user = userRepository.findByUserId(userId);

    if (user == null) {
      return false;
    }

    return user.isSamePassword(password);
  }

  public User getUserByUserId(String userId) {
    return userRepository.findByUserId(userId);
  }

  public String userInfoService(Long id, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return HttpSessionUtils.LOGIN_URL;
    }

    User sessionedUser = HttpSessionUtils.getUserFromSession(session);
    HttpSessionUtils.checkIdOfUserFromSession(id, sessionedUser, session);

    return "user/updateForm";
  }

  public String updateUserService(Long id, User updatedUser, HttpSession session){
    if (!HttpSessionUtils.isLoginUser(session)) {
      return HttpSessionUtils.LOGIN_URL;
    }

    User sessionedUser = HttpSessionUtils.getUserFromSession(session);
    HttpSessionUtils.checkIdOfUserFromSession(id, sessionedUser, session);

    User user = findUserById(id);

    if (user.isSamePassword(updatedUser)) {
      user.update(updatedUser);
      userRepository.save(user);

      return "redirect:/users";
    }

    return "redirect:/users/" + id + "/form";
  }
}
