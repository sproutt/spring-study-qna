package codesquad.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpUserDto {

    private String userId;
    private String password;
    private String name;
    private String email;
}
