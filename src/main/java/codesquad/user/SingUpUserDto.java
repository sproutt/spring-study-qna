package codesquad.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SingUpUserDto {

    private String userId;
    private String password;
    private String name;
    private String email;
}
