package codesquad.net.slipp.web.utils;


import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.exception.SessionNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotMatchException;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String SESSION_KEY = "userSession";

    public static boolean isLogin(HttpSession session) {
        if (session.getAttribute(SESSION_KEY) == null) {

            return false;
        }
        return true;
    }

    public static User getSessionUser(HttpSession session) {
        if (!isLogin(session)) {

            throw new SessionNotFoundException();
        }
        return (User) session.getAttribute(SESSION_KEY);
    }

    public static Long getSessionUserId(HttpSession session) {
        return getSessionUser(session).getId();
    }

    public static boolean isSessionMatch(HttpSession session, User user) {
        if (!getSessionUser(session).match(user)) {

            return false;
        }
        return true;
    }

    public static User getAuthUser(HttpSession session, User user) {
        if (!isSessionMatch(session, user)) {

            throw new SessionNotMatchException();
        }
        return getSessionUser(session);
    }

    public static void checkAuth(HttpSession session, User user) {
        if (!isSessionMatch(session, user)) {

            throw new SessionNotMatchException();
        }
    }
}
