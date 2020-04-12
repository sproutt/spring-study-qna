package codesquad.service;

import codesquad.domain.User;

import java.util.List;

public interface UserService {
    public abstract List<User> getUsers();

    public abstract void signUp(User user);

    public abstract User getUser(String userId);
}
