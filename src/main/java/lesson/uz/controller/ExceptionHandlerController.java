package lesson.uz.controller;

import lesson.uz.exp.AppBadRequestException;
import lesson.uz.exp.ItemNotFoundException;
import lesson.uz.exp.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({ItemNotFoundException.class, AppBadRequestException.class, UsernameAlreadyExistsException.class})
    public ResponseEntity<String> handle(RuntimeException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<String> handleRuntime(AuthorizationDeniedException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntime(RuntimeException e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
