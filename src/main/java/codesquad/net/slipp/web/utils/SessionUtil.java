package codesquad.net.slipp.web.utils;


import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.SessionNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static String SESSION_KEY = "userSession";

    public static void isLogin(HttpSession session) {
        Object sessionUser = session.getAttribute(SESSION_KEY);
        if (sessionUser == null) {

            throw new SessionNotFoundException();
        }
    }

    public static User getSessionUser(HttpSession session) {
        isLogin(session);

        return (User) session.getAttribute(SESSION_KEY);
    }

    public static boolean isSessionMatch(HttpSession session, User user) {
        User sessiondUser = getSessionUser(session);
        if (!sessiondUser.isSameUser(user)) {

            throw new SessionNotMatchException();
        }
        return true;
    }
}
