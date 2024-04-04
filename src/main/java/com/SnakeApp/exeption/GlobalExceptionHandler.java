package com.SnakeApp.exeption;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundHandling(ResourceNotFoundException ex, WebRequest request){

        ErrorResponse errors = new ErrorResponse();
        errors.setStatusCode(HttpStatus.NOT_FOUND.value());
        errors.setMessage(HttpStatus.NOT_FOUND.toString());
        errors.setDetails(ex.getMessage());
        errors.setTimestamp(new Date());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<ErrorResponse> resourceFoundHandling(ResourceFoundException ex, WebRequest request){

        ErrorResponse errors = new ErrorResponse();
        errors.setStatusCode(HttpStatus.CONFLICT.value());
        errors.setMessage(HttpStatus.CONFLICT.name());
        errors.setDetails(ex.getMessage());
        errors.setTimestamp(new Date());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestHandling(BadRequestException ex, WebRequest request){

        ErrorResponse errors = new ErrorResponse();
        errors.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errors.setMessage(HttpStatus.BAD_REQUEST.name());
        errors.setDetails(ex.getMessage());
        errors.setTimestamp(new Date());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

   /* @ExceptionHandler(MethodArgumentNotValidException.class)
       protected ResponseEntity<Object> handleMethodArgumentNotValid(
               MethodArgumentNotValidException ex, HttpHeaders headers,
               HttpStatus status, WebRequest request) {

           List<String> errors = ex.getBindingResult()
                   .getFieldErrors()
                   .stream()
                   .map(DefaultMessageSourceResolvable::getDefaultMessage)
                   .collect(Collectors.toList());

           ErrorResponse responses = new ErrorResponse();
           responses.setStatusCode(HttpStatus.BAD_REQUEST.value());
           responses.setMessage(HttpStatus.BAD_REQUEST.name());
           responses.setDetails(errors.toString());
           responses.setTimestamp(new Date());

           return new ResponseEntity<>(responses, HttpStatus.BAD_REQUEST);

       }
   */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandling(Exception ex, WebRequest request){

        ErrorResponse errors = new ErrorResponse();
        errors.setStatusCode( HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errors.setDetails(ex.getMessage());
        errors.setTimestamp(new Date());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
