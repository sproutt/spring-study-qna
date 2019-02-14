package codesquad.Model;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter @Setter
    private String userId;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String email;

    public boolean isSameUser(String userId) {
        if (this.userId.equals(userId)) {
            return true;
        }
        return false;
    }
}
