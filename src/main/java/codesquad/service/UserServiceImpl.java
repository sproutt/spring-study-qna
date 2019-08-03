package codesquad.service;

import codesquad.dao.UserDaoImpl;
import codesquad.dto.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDaoImpl users;

  @Override
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

  public void modifyUser(String userId, User user){
      users.modifyUser(userId, user);
  }

  public boolean isSamePassword(String userId, User user){
    return users.findUser(userId).getPassword() == user.getPassword();
  }
}
