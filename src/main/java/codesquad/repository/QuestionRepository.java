package codesquad.repository;

import codesquad.domain.Question;
import codesquad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByWriter(User writer);
}
