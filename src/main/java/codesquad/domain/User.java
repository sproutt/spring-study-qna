package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20, unique = true)
  private String userId;

  private String password;
  private String name;
  private String email;

  public boolean isSameUser(User other) {
    System.out.println("0");
    if (other == null) {
      return false;
    }

    if(!userId.equals(other.getUserId())){
      return false;
    }

    return password.equals(other.getPassword());
  }

  public boolean isSameId(Long otherId) {
    if (otherId == null) {
      return false;
    }

    return this.id.equals(otherId);
  }

  public boolean isSameUser(String otherPassword) {
    if (otherPassword == null) {
      return false;
    }

    return password.equals(otherPassword);
  }

  public void update(User newUser) {
    this.password = newUser.password;
    this.name = newUser.name;
    this.email = newUser.email;
  }
}
