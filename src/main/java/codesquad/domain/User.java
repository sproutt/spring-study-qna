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
    private String changePassword;


    public boolean isSamePasswordForEdit(User changedUser, User user) {
        if (changedUser.getPassword().equals(user.getPassword())) {
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
}
