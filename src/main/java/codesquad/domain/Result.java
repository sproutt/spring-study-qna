package codesquad.domain;

public class Result<T> {
    private T t;
    private String message;

    public Result(T t, String message) {
        System.out.println("t 값 = " + t.toString());
        this.t = t;
        this.message = message;
    }

    public static <T> Result<T> ok(T t) {
        return new Result<>(t, "ok");
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(null, message);
    }

    public T getT() {
        return t;
    }

    public String getMessage() {
        return message;
    }
}
