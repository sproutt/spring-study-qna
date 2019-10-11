package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;

    private String password;
    private String name;
    private String email;

    public boolean isSamePassword(String inputPassword) {
        return inputPassword.equals(this.password) ? true : false;
    }

    public User editProfile(User user, User loginUser) {
        loginUser.setName(user.getName());
        loginUser.setEmail(user.getEmail());
        return loginUser;
    }

    public boolean isSameUserId(User loginUser) {
        return this.userId.equals(loginUser.getUserId()) ? true : false;
    }
}
