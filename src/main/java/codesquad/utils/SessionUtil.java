package codesquad.utils;

import codesquad.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static User getUserBySession(HttpSession session) {
        return (User) session.getAttribute("sessionedUser");
    }

    public static void setSession(HttpSession session, User user) {
        session.setAttribute("sessionedUser", user);
    }
}
