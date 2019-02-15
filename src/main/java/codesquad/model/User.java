package codesquad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public boolean isSameUser(String userId) {
        if (this.userId.equals(userId)) {
            return true;
        }
        return false;
    }
}
