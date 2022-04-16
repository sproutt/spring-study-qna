package codesquad.utils;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String SESSION_KEY = "sessionedUser";

    public static User getUserBySession(HttpSession session) {
        return (User) session.getAttribute(SESSION_KEY);
    }

    public static void setSession(HttpSession session, User user) {
        session.setAttribute(SESSION_KEY, user);
    }
}
