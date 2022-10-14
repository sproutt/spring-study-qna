package codesquad.repository;

import codesquad.UserNotFoundException;
import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final List<UserEntity> users = new ArrayList<>();

    @Override
    public void register(UserEntity userEntity) {
        users.add(userEntity);
    }

    @Override
    public void update(String userId, UserUpdateRequestDto userUpdateRequestDto) {
        for (UserEntity user : users) {
            if (user.isSameId(user, userId)) {
                user.updatePassword(userUpdateRequestDto.getPassword());
                user.updateName(userUpdateRequestDto.getName());
                user.updateEmail(userUpdateRequestDto.getEmail());
            }
        }
    }

    @Override
    public UserEntity findById(String userId) {
        for (UserEntity user : users) {
            if (user.isSameId(user, userId)) {
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
