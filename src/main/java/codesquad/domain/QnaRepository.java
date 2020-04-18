package codesquad.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QnaRepository {
    private List<Qna> qnas;

    public QnaRepository() {
        qnas = new ArrayList<>();
    }

    public void insert(Qna qna) {
        this.qnas.add(qna);
    }
}
