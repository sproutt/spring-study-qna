package codesquad.service;

import codesquad.dao.UserDao;
import codesquad.dto.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
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

  public void modifyUser(String userId, User user) {
    users.modifyUser(userId, user);
  }

  public boolean isSamePassword(String userId, User user) {
    return users.findUser(userId).getPassword()
        .equals(user.getPassword());
  }
}
