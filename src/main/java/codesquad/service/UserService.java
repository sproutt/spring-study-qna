package codesquad.service;

import codesquad.dao.UserRepository;
import codesquad.dto.User;
import java.util.ArrayList;
import java.util.List;
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

  public User findUser(Long Id) {
    return userRepository.findById(Id).get();
  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);

    return users;
  }

  private void modifyUser(Long id, User newUser) {

    User user = findUser(id);
    user.update(newUser);
    userRepository.save(user);
  }

  public String updateUser(Long id, User newUser) {
    User user = findUser(id);

    if (user.isSamePassword(newUser)) {
      modifyUser(id, newUser);

      return "redirect:/users";
    }

    return "redirect:/users/" + id + "/form";
  }
}
