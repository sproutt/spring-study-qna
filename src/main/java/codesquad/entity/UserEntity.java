package codesquad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private String userId;
    private String password;
    private String name;
    private String email;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean matchId(UserEntity user, String userId) {
        return userId.equals(user.getUserId());
    }
}
