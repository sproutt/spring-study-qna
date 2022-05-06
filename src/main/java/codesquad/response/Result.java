package codesquad.response;

import lombok.Getter;

@Getter
public class Result<T> {

    private final T result;
    private final String message;

    public Result(T answer, String message) {
        this.result = answer;
        this.message = message;
    }

    public static <T> Result<T> ok(T id) {
        return new Result<>(id, null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(null, message);
    }

}