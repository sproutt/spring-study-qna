package codesquad.utils;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionChecker {

    private static final String USER_SESSION = "sessionedUser";
    @Autowired
    UserRepository userRepository;

    public static boolean isThisSessionedWasLoggedin(HttpSession session) {
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
