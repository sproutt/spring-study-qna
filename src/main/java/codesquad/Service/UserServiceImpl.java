package codesquad.Service;

import codesquad.DAO.UserDaoImpl;
import codesquad.VO.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserDaoImpl users;

  public void addUser(User user) {
    users.insertUser(user);
  }

  @Override
  public User findUser(String userId) {
    return users.findUser(userId);
  }

  @Override
  public List<User> getUsers() {
    return new ArrayList<>(users.getUsers());
  }
}
