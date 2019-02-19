package codesquad.net.slipp.web.result;

import codesquad.net.slipp.web.domain.Answer;
import org.json.simple.JSONObject;

public class Result {

    public JSONObject ok(Answer answer){
        JSONObject json = new JSONObject();
        json.put("result", "ok");
        json.put("data", answer);

        return json;
    }

    public JSONObject fail(String errorMessage){
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        json.put("data", errorMessage);

        return json;
    }
}
