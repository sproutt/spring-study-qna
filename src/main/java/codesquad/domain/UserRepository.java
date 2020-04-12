package codesquad.domain;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public List<User> list() {
        return users;
    }

    public void insert(User user) {
        users.add(user);
    }

    public User select(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
