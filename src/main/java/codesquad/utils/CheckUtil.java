package codesquad.utils;

import codesquad.model.question.Question;
import codesquad.model.user.User;

import java.util.Optional;

public class CheckUtil {

    public static boolean isWriter(Optional<User> user, Optional<Question> question) {
        if (user.get().getId().equals(question.get().getWriter().getId())) {
            return true;
        }
        return false;
    }

    public static boolean isUserPassword(Optional<User> user, String password) {
        if (user.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
