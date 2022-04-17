package codesquad.util;

import codesquad.domain.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession httpSession) {
        Object sessionedUser = httpSession.getAttribute(SESSION_KEY);

        if (sessionedUser == null) {
            return false;
        }

        return true;
    }

    public static User getUserFrom(HttpSession httpSession) {
        return (User) httpSession.getAttribute(SESSION_KEY);
    }
}
