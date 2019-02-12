package codesquad.net.slipp.web.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(long id) {
        super("{ ERROR_CODE : " + 200 + ", PK : " + id + " }");
    }

}
