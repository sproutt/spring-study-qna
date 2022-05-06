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

    User(SignUpUserDto signUpUserDto) {
        userId = signUpUserDto.getUserId();
        password = signUpUserDto.getPassword();
        name = signUpUserDto.getName();
        email = signUpUserDto.getEmail();
    }

    public void update(SignUpUserDto user) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
