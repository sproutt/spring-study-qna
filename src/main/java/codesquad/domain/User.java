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
        return this.password.equals(inputPassword);
    }

    public void editProfile(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public boolean isSameUserId(User loginUser) {
        return this.userId.equals(loginUser.getUserId());
    }
}
