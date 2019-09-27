package codesquad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResponseException extends RuntimeException{

  public ResponseException(String message){
    super(message);
  }

}
