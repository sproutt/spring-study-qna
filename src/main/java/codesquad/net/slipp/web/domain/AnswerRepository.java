package codesquad.net.slipp.web.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findByQuestionId(long questionId);

    @Query("Select writer from Answer answer where answer.id = ?1")
    Optional<User> findUserById(long id);
}

