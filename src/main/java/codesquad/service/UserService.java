package codesquad.service;

import codesquad.exception.NullUserException;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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

    public User findSessionUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public void update(User user, User newUser) {
        user.update(newUser);
        save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
