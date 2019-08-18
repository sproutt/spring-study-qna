package codesquad.dao;

import codesquad.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByUserId(String userId);
}
