package codesquad.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    UserRepository userRepository;

    public UserDaoImpl() {
        this.userRepository = new UserRepository();
    }

    @Override
    public List<User> selectAll() {
        return userRepository.list();
    }

    @Override
    public void insert(User user) {
        userRepository.insert(user);
    }

    @Override
    public User select(String userId) {
        return userRepository.select(userId);
    }
}
