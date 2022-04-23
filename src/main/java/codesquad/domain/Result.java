package codesquad.domain;

public class Result<T> {

    private T data;
    private String errorMessage;

    private Result(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> fail(String errorMessage) {
        return new Result<>(null, errorMessage);
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
