package codesquad.Service;

import codesquad.VO.User;
import java.util.List;

public interface UserService {

  public void addUser(User user);

  public User findUser(String userId);

  public List<User> getUsers();
}
