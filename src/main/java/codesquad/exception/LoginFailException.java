package codesquad.exception;

public class LoginFailException extends LoginException {

  public LoginFailException() {

    super("아이디/비밀번호가 틀립니다");

  }
}
