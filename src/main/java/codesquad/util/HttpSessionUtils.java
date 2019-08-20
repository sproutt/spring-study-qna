package codesquad.util;

import codesquad.dto.Question;
import codesquad.dto.User;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

  public static final String USER_SESSION_KEY = "sessionedUser";

  public static void checkLogining(HttpSession session) {
    Object sessionedUser = session.getAttribute(USER_SESSION_KEY);

    if (sessionedUser == null) {
      new IllegalStateException("로그인 후 이용가능 합니다");
    }
  }

  public static void checkIdOfUserFromSession(Long id, HttpSession session) {
    checkLogining(session);

    User sessionedUser = (User) session.getAttribute(USER_SESSION_KEY);

    if (!sessionedUser.isSameId(id)) {
      throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
    }
  }

  public static void checkWriterOfQuestionFromSession(Question question, HttpSession session) {
    checkLogining(session);

    User user = (User) session.getAttribute(USER_SESSION_KEY);

    if (!question.isSameWriter(user)) {
      throw new IllegalStateException("자신의 질문이 아닙니다");
    }
  }
}
