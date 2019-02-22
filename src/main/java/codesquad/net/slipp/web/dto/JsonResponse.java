package codesquad.net.slipp.web.dto;

import codesquad.net.slipp.web.domain.Answer;
import org.json.simple.JSONObject;

public class JsonResponse {
    private static final String SUCESS_MESSAGE= "ok";
    private static final String FAIL_MESSAGE="fail";

    public static JSONObject ok(Answer answer) {
        JSONObject json = new JSONObject();
        json.put("result", SUCESS_MESSAGE);
        json.put("data", answer);

        return json;
    }

    public static JSONObject ok(Long answerId) {
        JSONObject json = new JSONObject();
        json.put("result", SUCESS_MESSAGE);
        json.put("data", answerId);

        return json;
    }

    public static JSONObject fail(String errorMessage) {
        JSONObject json = new JSONObject();
        json.put("result", FAIL_MESSAGE);
        json.put("data", errorMessage);

        return json;
    }
}
