package codesquad.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

  public static String encode(String password){
    return new BCryptPasswordEncoder().encode(password);
  }

  public static boolean isSame(String hashedPassword, String password){
    return new BCryptPasswordEncoder().matches(password,hashedPassword);
  }
}
