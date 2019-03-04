package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkIdPassword(User user) {
        String userId = user.getUserId();
        User modelUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        return modelUser.match(user.getPassword());
    }

    public boolean checkIdPassword(String userId, String password) {
        User modelUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        return modelUser.match(password);
    }

    public User findByUserId(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        return user;
    }

    public User findById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void update(User modelUser, User updateUser, String modifiedPassword) {
        updateUser.setPassword(modifiedPassword);
        modelUser.update(updateUser);
        save(modelUser);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
