package codesquad.util;

import codesquad.dto.Question;
import codesquad.dto.User;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
  public static final String USER_SESSION_KEY = "sessionedUser";
  public static final String LOGIN_URL = "redirect:/users/login/form";

  public static boolean isLoginUser(HttpSession session){
    Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
    if(sessionedUser == null){
      return false;
    }

    return true;
  }

  public static boolean isSameWriterFromSession(Question question, HttpSession session){
    if(!isLoginUser(session)){
      return false;
    }

    return question.isSameWriter(getUserFromSession(session));
  }

  public static User getUserFromSession(HttpSession session){
    if(!isLoginUser(session)){
      return null;
    }

    return (User)session.getAttribute(USER_SESSION_KEY);
  }

  public static void checkIdOfUserFromSession(Long id, User user, HttpSession session){
    if (!user.isSameId(id)) {
      throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
    }
  }

  public static void checkWriterOfQuestionFromSession(Long id, Question question, HttpSession session){
    if (!HttpSessionUtils.isSameWriterFromSession(question, session)) {
      throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
    }
  }
}
