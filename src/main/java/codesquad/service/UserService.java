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

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);

    return users;
  }

  public String updateUser(Long id, User updatedUser) {
    User user = findUserById(id);

    if (user.isSamePassword(updatedUser)) {
      user.update(updatedUser);
      userRepository.save(user);

      return "redirect:/users";
    }

    return "redirect:/users/" + id + "/form";
  }

  public boolean isUserFromDB(String userId, String password) {
    User user = userRepository.findByUserId(userId);

    if (user == null) {
      return false;
    }

    return user.getPassword().equals(password); //refac
  }

  public User getUserByUserId(String userId) {
    return userRepository.findByUserId(userId);
  }

}
