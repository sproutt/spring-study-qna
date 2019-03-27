package codesquad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result {
    private long answerId;

    public Result fail(String message) {
        return null;
    }

    public Result ok() {
        return new Result(answerId);
    }
}
