package codesquad.service;

import codesquad.dto.UserDto;
import codesquad.entity.UserEntity;

import java.util.List;

public interface UserService {
    void registerUser(UserDto userDto);

    UserEntity findUser(String userId);

    List<UserEntity> findUsers();
}
