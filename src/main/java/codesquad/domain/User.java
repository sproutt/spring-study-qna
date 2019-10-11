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

    public boolean isSamePassword(User user, User loginUser) {
        if (loginUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean isSamePasswordForLogin(String loginpassword, User user) {
        if (loginpassword.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    public User editProfile(User user, User loginUser) {
        loginUser.setName(user.getName());
        loginUser.setEmail(user.getEmail());
        return loginUser;
    }
}
