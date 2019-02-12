package codesquad.net.slipp.web.utils;

import org.springframework.stereotype.Service;

@Service
public class ErrorMessageUtils {
    final private String getNotLogin ="로그인 후 사용가능합니다.";
    final private String getNotMatchSession = "본인의 아이디가 아닙니다.";
    final private String getWrongPassword = "잘못된 비밀번호입니다.";

    public String getGetNotLogin() {
        return getNotLogin;
    }

    public String getGetNotMatchSession() {
        return getNotMatchSession;
    }

    public String getGetWrongPassword() {
        return getWrongPassword;
    }
}
