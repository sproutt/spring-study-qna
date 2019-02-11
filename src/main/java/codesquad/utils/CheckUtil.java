package codesquad.utils;

import codesquad.model.question.Question;
import codesquad.model.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CheckUtil {

    public static boolean sessionNullChecker(String object, HttpSession session) {
        if (session.getAttribute(object) == null) {
            return true;
        }
        return false;
    }

    public static boolean questionNullChecker(Optional<Question> question) {
        if (question.isPresent()) {
            return false;
        }
        return true;
    }

    public static boolean userNullChecker(Optional<User> user) {
        if (user.isPresent()) {
            return false;
        }
        return true;
    }

    public static boolean writerChecker(Optional<User> user, Optional<Question> question) {
        if (user.get().getId().equals(question.get().getWriter().getId())) {
            return true;
        }
        return false;
    }

    public static boolean userPasswordChecker(Optional<User> user, String password) {
        if (user.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
