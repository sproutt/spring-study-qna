package codesquad.service;

import codesquad.dao.UserDao;
import codesquad.dto.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Resource(name = "userDao")
  private UserDao users;

  public void addUser(User user) {
    users.insertUser(user);
  }

  public User findUser(String userId) {
    return users.findUser(userId);
  }

  public List<User> getUsers() {
    return new ArrayList<>(users.getUsers());
  }

  private void modifyUser(String userId, User user) {
    users.modifyUser(userId, user);
  }

  public String updateUserUrl(String userId, User user) {
    if (findUser(userId).isSamePassword(user)) {
      modifyUser(userId, user);

      return "redirect:/users";
    }

    return "redirect:/" + userId + "/form";
  }
}
