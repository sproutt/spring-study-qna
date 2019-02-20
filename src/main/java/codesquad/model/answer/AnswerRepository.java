package codesquad.model.answer;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer,Long> {
    Iterable<Answer> findByQuestionId(Long id);
}
