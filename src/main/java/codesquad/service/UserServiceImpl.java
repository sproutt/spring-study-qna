package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUsers() {
        return userDao.selectAll();
    }

    @Override
    public void signUp(User user) {
        userDao.insert(user);
    }

    @Override
    public User getUser(String userId) {
        return userDao.select(userId);
    }
}
