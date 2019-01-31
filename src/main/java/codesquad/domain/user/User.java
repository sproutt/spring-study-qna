package codesquad.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String email;

    public void update(User modifiedUser) {
        this.userId = modifiedUser.userId;
        this.name = modifiedUser.name;
        this.email = modifiedUser.email;
    }

    public boolean match(String password) {
        if(this.password.equals(password)) return true;
        return false;
    }

    public boolean match(Long id) {
        if(this.id == id) return true;
        return false;
    }
}
