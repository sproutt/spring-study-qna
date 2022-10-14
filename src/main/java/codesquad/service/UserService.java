package codesquad.service;

import codesquad.entity.UserEntity;

import java.util.List;

public interface UserService {
    void registerUser(UserEntity userEntity);

    void updateUser(String userId, UserEntity userEntity);

    UserEntity findUser(String userId);

    List<UserEntity> findUsers();
}
