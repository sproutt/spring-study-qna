package codesquad.service;

import codesquad.exception.UserNotFoundException;
import codesquad.model.User;
import codesquad.repository.UserRepository;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    public boolean isSameUserSessioned(Long id, HttpSession session) {
        return HttpSessionUtils.getSessionedUser(session).isSameUser(id);
    }
}
