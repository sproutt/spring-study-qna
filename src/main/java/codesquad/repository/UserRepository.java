package codesquad.repository;

import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    void register(UserEntity userEntity);

    void update(String userId, UserUpdateRequestDto userUpdateRequestDto);

    UserEntity findById(String userId);

    List<UserEntity> findAll();
}
