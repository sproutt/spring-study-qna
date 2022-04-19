package codesquad.qua;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select count(q.id) from Question q" +
            " join Answer a" +
            " on a.question.id=q.id" +
            " where a.deletedFlag=false and q.id=:id")
    Long countNotDeletedAnswers(@Param("id") long questionId);
}
