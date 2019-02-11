package codesquad.utils;

import codesquad.model.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {

    public static Optional<User> getSessionUser(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute("user"));
    }

}