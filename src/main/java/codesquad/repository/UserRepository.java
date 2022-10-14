package codesquad.repository;

import codesquad.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    void register(UserEntity userEntity);

    void update(String userId, UserEntity userEntity);

    UserEntity findById(String userId);

    List<UserEntity> findAll();
}
