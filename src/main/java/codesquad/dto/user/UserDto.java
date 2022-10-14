package codesquad.dto.user;

import codesquad.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserDto(UserEntity userEntity) {
        userId = userEntity.getUserId();
        password = userEntity.getPassword();
        name = userEntity.getName();
        email = userEntity.getEmail();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
