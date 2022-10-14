package codesquad.mapper;

import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;

public class UserMapper {
    public static UserEntity dtoToEntity(UserUpdateRequestDto userUpdateRequestDto) {
        if (userUpdateRequestDto == null) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userId(userUpdateRequestDto.getPassword());
        userEntity.name(userUpdateRequestDto.getName());
        userEntity.email(userUpdateRequestDto.getEmail());

        return userEntity.build();
    }
}