package codesquad.util;

import codesquad.controller.LoginException;
import codesquad.domain.User;
import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

  public static final String USER_SESSION_KEY = "sessionedUser";

  public static void setUserFromSession(HttpSession session, User user) {
    session.setAttribute("sessionedUser", user);
  }

  public static void removeUserFromSession(HttpSession session) {
    session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
  }

  public static void checkLogining(HttpSession session) {
    Object sessionedUser = session.getAttribute(USER_SESSION_KEY);

    if (sessionedUser == null) {
      throw new LoginException("로그인이 필요합니다아아");
    }
  }

  public static User getUserFromSession(HttpSession session) {
    return (User) session.getAttribute(USER_SESSION_KEY);
  }
}
