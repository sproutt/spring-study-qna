package codesquad.service;

import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;

import java.util.List;

public interface UserService {
    void registerUser(UserEntity userEntity);

    void updateUser(String userId, UserUpdateRequestDto requestDto);

    UserEntity findUser(String userId);

    List<UserEntity> findUsers();
}
