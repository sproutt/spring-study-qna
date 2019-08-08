package codesquad.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private String userId;
  private String password;
  private String name;
  private String email;

  public boolean isSamePassword(User user){
    return this.password.equals(user.getPassword());
  }
}
