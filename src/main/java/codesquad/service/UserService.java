package codesquad.service;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findByUserId(String userId) {
        Optional<User> byUserId = userRepository.findByUserId(userId);
        return byUserId.orElseThrow(() -> new UserNotFoundException(userId));
    }
}
