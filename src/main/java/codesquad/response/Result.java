package codesquad.response;

public class Result<T> {

    private T result;
    private String message;

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

    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}