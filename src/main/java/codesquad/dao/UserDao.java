package codesquad.dao;

import codesquad.dto.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

  private Map<String, User> userDB;

  public UserDao() {
    userDB = new HashMap<>();
  }

  public void insertUser(User user) {
    userDB.put(user.getUserId(), user);
  }

  public User findUser(String userId) {
    return userDB.get(userId);
  }

  public Collection<User> getUsers() {
    return userDB.values();
  }

  public void modifyUser(String userId, User user){
    userDB.put(userId, user);
  }
}
