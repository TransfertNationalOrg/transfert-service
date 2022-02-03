package ma.ensa.controller.advice;

import ma.ensa.exception.TransfertDuplicatedException;
import ma.ensa.exception.TransfertNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(TransfertNotFoundException.class)
    private ResponseEntity<?> handleNotFoundException(TransfertNotFoundException exception){
        String responseMessage = "The provided transfert ["+exception.getId()+"] is nowhere to be found.";
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(responseMessage);
    }

    @ExceptionHandler(TransfertDuplicatedException.class)
    private ResponseEntity<?> handleMovieDuplicatedException(TransfertDuplicatedException exception){
        String responseMessage = "The provided transfert ["+exception.getId()+"] is already existing.";
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(responseMessage);
    }
}