package codesquad.net.slipp.web.dto;

import codesquad.net.slipp.web.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JsonResponse<T> {
    private static final String FAIL = "fail";
    private static final String SUCESS = "ok";

    private String status;
    private T data;

    public static JsonResponse<Answer> ok(Answer answer) {
        return new JsonResponse<>(SUCESS, answer);
    }

    public static JsonResponse<String> fail(String errorMessage) {
        return new JsonResponse<>(FAIL, errorMessage);
    }
}
