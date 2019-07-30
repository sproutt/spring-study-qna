package codesquad.DAO;

import codesquad.VO.User;
import java.util.Collection;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

  private HashMap<String, User> userDB;

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

  public Collection<User> getUsers() {
    return userDB.values();
  }
}
