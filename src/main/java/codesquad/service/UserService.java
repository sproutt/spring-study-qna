package codesquad.service;

import codesquad.exception.NullUserException;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NullUserException(id));
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void update(Long id, User newUser) {
        User user = findById(id);
        user.update(newUser);
        save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
