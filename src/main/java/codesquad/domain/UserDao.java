package codesquad.domain;

import java.util.List;

public interface UserDao {
    public abstract List<User> selectAll();

    public abstract void insert(User user);

    public abstract User select(String userId);
}
