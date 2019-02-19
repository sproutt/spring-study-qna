package codesquad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column
    private String email;

    public void update(User updatedUser) {
        this.password = updatedUser.getPassword();
        this.name = updatedUser.getName();
        this.email = updatedUser.getEmail();
    }

    public boolean isCorrectPassword(String password) {

        if (this.password.equals(password)) {
            return true;
        }

        return false;
    }
}
