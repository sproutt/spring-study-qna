package codesquad.dao;

import codesquad.dto.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
