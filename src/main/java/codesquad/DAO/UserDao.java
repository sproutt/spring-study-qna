package codesquad.DAO;

import codesquad.VO.User;
import java.util.Collection;

public interface UserDao {

  public void insertUser(User user);

  public User findUser(String userId);

  public Collection<User> getUsers();

}
