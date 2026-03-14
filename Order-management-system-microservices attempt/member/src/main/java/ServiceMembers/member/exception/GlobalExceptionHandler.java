package ServiceMembers.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> illegalArgument(MethodArgumentNotValidException me)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input has been given");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> noMemberFound(MemberNotFoundException mnf)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mnf.getMessage());
    }
}
