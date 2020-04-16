package codesquad.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    private String password;
    private String changedPassword;
    private String name;
    private String email;

    @Builder
    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean isSameUser(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public boolean isSamePassword(User user) {
        return this.password.equals(user.getPassword());
    }

    public boolean checkPassword(String password) {
        if (password == null) {
            return false;
        }
        return this.password.equals(password);
    }

    public void changeUserInfo(User user) {
        this.password = user.getChangedPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
