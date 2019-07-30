package codesquad.DAO;

import codesquad.VO.User;
import java.util.Collection;

public interface UserDao {

  void insertUser(User user);

  User findUser(String userId);

  Collection<User> getUsers();

}
