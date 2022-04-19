package codesquad.qua;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "select count(*) " +
            "from Question q right outer join Answer a " +
            "on q.id=a.id " +
            "where a.deleted_flag=false", nativeQuery = true)
    int countNotDeletedAnswers(long id);
}
