package codesquad.domain;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute("sessionedUser") != null;
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("sessionedUser");
    }
}
