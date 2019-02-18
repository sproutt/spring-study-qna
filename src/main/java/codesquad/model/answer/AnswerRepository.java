package codesquad.model.answer;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer,Long> {
    Iterable<Answer> findByQuestionId(Long id);
}
