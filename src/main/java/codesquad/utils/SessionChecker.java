package codesquad.utils;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class SessionChecker {

    @Autowired
    UserRepository userRepository;

    public boolean isThisSessionedWasLoggedin(HttpSession session) {
        if (session.getAttribute("sessionedUser") == null) {
            return false;
        } else {
            return true;
        }
    }

    public User loggedinUser(HttpSession session) {
        return (User) session.getAttribute("sessionedUser");
    }

}
