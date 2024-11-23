package tech.solutionhut.ecommerce.customer.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.solutionhut.ecommerce.customer.exception.CustomerNotFoundException;
import tech.solutionhut.ecommerce.customer.record.ErrorResponse;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handle(CustomerNotFoundException exp) {
        return new ResponseEntity<>(exp.getMsg(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult()
           .getAllErrors()
           .forEach(error -> {
               var fieldName = ((FieldError) error).getField();
               var errorMessage = error.getDefaultMessage();
               errors.put(fieldName, errorMessage);
           });
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
