package codesquad.dao;

import codesquad.dto.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

  private Map<String, User> userDB;

  public UserDaoImpl() {
    userDB = new HashMap<>();
  }

  @Override
  public void insertUser(User user) {
    userDB.put(user.getUserId(), user);
  }

  @Override
  public User findUser(String userId) {
    return userDB.get(userId);
  }

  @Override
  public Collection<User> getUsers() {
    return userDB.values();
  }
}
