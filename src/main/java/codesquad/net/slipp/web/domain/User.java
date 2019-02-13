package codesquad.net.slipp.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    @Getter @Setter
    private String userId;

    @Column(nullable = false, columnDefinition = "CHAR(64)")
    @Getter @Setter
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    @Getter @Setter
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
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
