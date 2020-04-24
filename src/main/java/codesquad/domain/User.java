package codesquad.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
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
    private String name;
    private String email;

    @Builder
    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public boolean isSamePassword(String password) {
        if (password == null) {
            return false;
        }
        return this.password.equals(password);
    }

    public void changeUserInfo(String changedPassword, String name, String email) {
        this.password = changedPassword;
        this.name = name;
        this.email = email;
    }
}
