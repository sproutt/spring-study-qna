package codesquad.service;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public void create(String userId, String password, String name, String email) {
        userRepository.save(new User()
                .builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build());
    }

    public Iterable<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.findById(id).get();
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean isSessionedUser(HttpSession session) {
        return HttpSessionUtils.isLoginUser(session);
    }

    public User getLoginUser(Long id, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isSameId(id)) {
            throw new IllegalStateException("Go Away.");
        }
        return sessionedUser;
    }

    public boolean checkPassword(String password, HttpSession session) {
        if (HttpSessionUtils.getUserFromSession(session).isSamePassword(password)) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String userId, String password) {
        if (userRepository.findByUserId(userId).isSamePassword(password)) {
            return true;
        }
        return false;
    }

    public void updateUser(String changedPassword, String name, String email, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        sessionedUser.changeUserInfo(changedPassword, name, email);
        userRepository.save(sessionedUser);
    }
}

