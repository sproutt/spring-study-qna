package codesquad.utils;

import codesquad.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    private static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isSessionedUser(HttpSession session) {
        if (session.getAttribute(USER_SESSION_KEY) == null) {
            return false;
        }

        return true;
    }

    public static User getSessionedUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static boolean checkSessionUser(Long id, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return false;
        }

        if (!HttpSessionUtils.getSessionedUser(session).isSameUser(id)) {
            throw new IllegalStateException("Wrong access!");
        }

        return true;
    }
}
