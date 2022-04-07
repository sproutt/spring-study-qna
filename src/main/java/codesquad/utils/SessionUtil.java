package codesquad.utils;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String SESSION_NAME = "sessionedUser";

    public static User getUserBySession(HttpSession session) {
        return (User) session.getAttribute(SESSION_NAME);
    }

    public static void setSession(HttpSession session, User user) {
        session.setAttribute(SESSION_NAME, user);
    }
}
