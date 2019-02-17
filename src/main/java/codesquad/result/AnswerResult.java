package codesquad.result;

public class AnswerResult {
    private static final String OK_MESSAGE = "답변 삭제 성공";
    private static final String FAIL_MESSAGE = "답변 삭제 실패";
    private Long answerId;
    private String resultMessage;

    public AnswerResult(Long answerId, String resultMessage) {
        this.answerId = answerId;
        this.resultMessage = resultMessage;
    }

    public static AnswerResult ok(Long answerId) {
        return new AnswerResult(answerId, OK_MESSAGE);
    }

    public static AnswerResult fail(Long answerId) {
        return new AnswerResult(answerId, FAIL_MESSAGE);
    }

}
