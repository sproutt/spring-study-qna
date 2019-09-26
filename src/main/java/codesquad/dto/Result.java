package codesquad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {

  private T content;
  private String error;

  public static <T>Result<T> ok(T t){
    return new Result(t, null);
  }

  public static Result<Object> fail(String errorMessage) {
    return new Result(null, errorMessage);
  }

}
