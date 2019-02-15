package codesquad.net.slipp.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Column(nullable = false)
    @Lob
    @Getter @Setter
    private String userId;

    @Column(nullable = false)
    @Lob
    @Getter @Setter
    private String password;

    @Column(nullable = false)
    @Lob
    @Getter @Setter
    private String name;

    @Column(nullable = false)
    @Lob
    @Getter @Setter
    private String email;

    public boolean match(User user) {

        return this.getUserId() == user.getUserId();
    }

    public boolean match(String password) {

        return this.password.equals(password);
    }

    public void update(User modifiedUser) {
        this.password = modifiedUser.password;
        this.name = modifiedUser.name;
        this.email = modifiedUser.email;
    }
}
