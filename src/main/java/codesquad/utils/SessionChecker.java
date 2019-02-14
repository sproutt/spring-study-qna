package codesquad.utils;

import codesquad.exception.UserNotLoginException;
import codesquad.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionChecker {

    private static final String USER_SESSION = "sessionedUser";

    public static boolean isLoggedIn(HttpSession session) {
        if (session.getAttribute(USER_SESSION) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static User loggedinUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION);
    }

}
