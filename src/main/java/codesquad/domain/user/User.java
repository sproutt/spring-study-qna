package codesquad.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public void update(User modifiedUser) {
        this.userId = modifiedUser.userId;
        this.name = modifiedUser.name;
        this.email = modifiedUser.email;
    }

    public boolean match(String password) {
        return this.password.equals(password);
    }

    public boolean match(Long id) {
        return this.id == id;
    }
}
