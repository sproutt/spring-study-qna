package codesquad.user;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;

    private String password;

    private String name;

    private String email;

    User(SingUpUserDto singUpUserDto) {
        userId = singUpUserDto.getUserId();
        password = singUpUserDto.getPassword();
        name = singUpUserDto.getName();
        email = singUpUserDto.getEmail();
    }

    public void update(User user) {
        if (equalsPassword(user.getPassword())) {
            name = user.getName();
            email = user.getEmail();
        }
    }

    public boolean equalsPassword(String password) {
        return this.password.equals(password);
    }

    public boolean equalsPassword(LoginUserDto loginUserDto) {
        return password.equals(loginUserDto.getPassword());
    }

    public boolean equalsId(Long id) {
        return this.id.equals(id);
    }
}
