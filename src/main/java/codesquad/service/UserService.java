package codesquad.service;

import codesquad.domain.User;

public interface UserService {
    void create(String userId, String password, String name, String email);

    Iterable<User> findUsers();

    User findUser(Long id);

    User findUser(String userId);

    boolean isSessionedUser();

    User getLoginUser(Long id);

    boolean checkPassword(User user);

    boolean checkPassword(String password);

    void updateUser(User user);
}

