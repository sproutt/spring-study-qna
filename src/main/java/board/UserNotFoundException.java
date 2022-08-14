package board;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(id +"의 사용자를 찾을 수 없습니다.");
    }
}
