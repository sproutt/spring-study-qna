package codesquad.domain;

import lombok.*;
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


    public boolean isSamePassword(User changedUser, User user) {
        if (changedUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
