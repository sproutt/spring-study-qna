package codesquad.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public List<User> findAll() {
        return users;
    }

    public void insert(User user) {
        users.add(user);
    }

    public User find(String userId) {
        return users.stream()
                .filter(user -> user.isSameId(userId))
                .findFirst()
                .get();
    }
}