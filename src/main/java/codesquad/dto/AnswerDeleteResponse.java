package codesquad.dto;

import lombok.Getter;

@Getter
public class AnswerDeleteResponse {
    private Long answerId;
    private boolean valid;
    private String errorMessage;

    private AnswerDeleteResponse(Long answerId, boolean valid, String errorMessage) {
        this.answerId = answerId;
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static AnswerDeleteResponse ok(Long answerId) {
        return new AnswerDeleteResponse(answerId, true, null);
    }

    public static AnswerDeleteResponse fail(String errorMessage) {
        return new AnswerDeleteResponse(null, false, errorMessage);
    }
}
