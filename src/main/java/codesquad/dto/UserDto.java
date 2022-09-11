package codesquad.dto;

import codesquad.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

    public UserEntity toEntity() {
        return new UserEntity(userId, name, password, email);
    }
}
