package codesquad.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse {
    private static final String OK_MESSAGE = "답변 삭제 성공";
    private static final String FAIL_MESSAGE = "답변 삭제 실패";
    private Long answerId;
    private String resultMessage;

    public RestResponse(Long answerId, String resultMessage) {
        this.answerId = answerId;
        this.resultMessage = resultMessage;
    }

    public static RestResponse ok(Long answerId) {
        return new RestResponse(answerId, OK_MESSAGE);
    }

    public static RestResponse fail(Long answerId) {
        return new RestResponse(answerId, FAIL_MESSAGE);
    }

}
