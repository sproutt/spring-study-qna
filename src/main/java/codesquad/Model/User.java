package codesquad.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
