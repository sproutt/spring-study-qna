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

    private HttpSession session;

    public void create(String userId, String password, String name, String email) {
        userRepository.save(new User().builder().userId(userId).password(password).name(name).email(email).build());
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

    public boolean isSessionedUser() {
        return HttpSessionUtils.isLoginUser(session);
    }

    public User getLoginUser(Long id) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isSameId(id)) {
            throw new IllegalStateException("Go Away.");
        }
        return sessionedUser;
    }

    public boolean checkPassword(User user) {
        if (HttpSessionUtils.getUserFromSession(session).isSamePassword(user)) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String password) {
        if (HttpSessionUtils.getUserFromSession(session).isSamePassword(password)) {
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        sessionedUser.changeUserInfo(user);
        userRepository.save(sessionedUser);
    }
}

