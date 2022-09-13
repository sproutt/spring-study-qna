package codesquad.repository;

import codesquad.UserNotFoundException;
import codesquad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ArrayUserRepository implements UserRepository {
    private final List<UserEntity> users = new ArrayList<>();

    @Override
    public void register(UserEntity userEntity) {
        users.add(userEntity);
    }

    @Override
    public UserEntity findById(String userId) {
        for (UserEntity user : users) {
            if (userId.equals(user.getUserId())) {
                return user;
            }
        }
        throw new UserNotFoundException("일치하는 아이디가 없습니다.");
    }

    @Override
    public List<UserEntity> findAll() {
        return users;
    }
}
