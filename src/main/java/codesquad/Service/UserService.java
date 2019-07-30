package codesquad.Service;

import codesquad.VO.User;
import java.util.List;

public interface UserService {

  User findUser(String userId);

  List<User> getUsers();
}
