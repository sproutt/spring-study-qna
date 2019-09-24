package codesquad.util;

import codesquad.dto.ResponseDTO;
import codesquad.dto.ErrorDTO;

public class ResponseUtil {

  public static <T> ResponseDTO<T>ok(T response) {
    return new ResponseDTO<>(response);
  }

  public static ErrorDTO fail(String error) {
    return new ErrorDTO(error);
  }

}
