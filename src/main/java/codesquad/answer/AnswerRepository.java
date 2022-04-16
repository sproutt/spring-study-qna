package codesquad.answer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @EntityGraph(attributePaths = {"question"})
    Optional<Answer> findFetchJoinById(Long id);
}
