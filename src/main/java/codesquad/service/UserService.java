package codesquad.service;

import codesquad.dao.UserRepository;
import codesquad.domain.User;
import codesquad.exception.LoginFailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void addUser(User user) {
    makeSecretPassword(user);
    userRepository.save(user);
  }

  private void makeSecretPassword(User user){
    user.toSecret(passwordEncoder);
  }

  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> (new IllegalStateException("데이터를 찾을 수 없습니다.")));
  }

  public User findUserByUserId(String userId) {
    return userRepository.findByUserId(userId);
  }

  public Iterable<User> findAllUsers() {
    return userRepository.findAll();
  }

  public void checkUserFromDB(String userId, String password) {
    User user = userRepository.findByUserId(userId);

    if (user == null || !user.isSamePassword(password, passwordEncoder)) {
      throw new LoginFailException();
    }
  }

  public String updateUserService(Long id, User updatedUser) {

    User user = findUserById(id);

    if (user.isSamePassword(updatedUser, passwordEncoder)) {
      user.update(updatedUser);
      userRepository.save(user);

      return "redirect:/users";
    }

    return "redirect:/users/" + id + "/form";
  }

  public void checkIdOfSession(User userFromSession, Long id) {
    userFromSession.checkId(id);
  }
}
