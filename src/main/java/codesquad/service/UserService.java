package codesquad.service;

import codesquad.domain.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    void create(String userId, String password, String name, String email);

    Iterable<User> findUsers();

    User findUser(Long id);

    User findUser(String userId);

    boolean isSessionedUser(HttpSession session);

    User getLoginUser(Long id, HttpSession session);

    boolean checkPassword(String password, HttpSession session);

    boolean checkPassword(String userId, String password);

    void updateUser(String changedPassword, String name, String email, HttpSession session);
}

