package codesquad.net.slipp.web.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(long id) {
        super("ERROR : "+id + "번째 게시글이 존재하지 않습니다.");
    }

}
