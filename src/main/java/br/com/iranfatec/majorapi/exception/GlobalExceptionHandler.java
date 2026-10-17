package br.com.iranfatec.majorapi.exception;

import br.com.iranfatec.majorapi.service.ActiveNameAlreadyExistsException;
import br.com.iranfatec.majorapi.service.ServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ActiveNameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> activeNameAlreadyExistsException(ActiveNameAlreadyExistsException exception) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> InvalidRequestException(MethodArgumentNotValidException methodArgumentNotValidException) {

        String messageError = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                messageError
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity <ApiErrorResponse> serviceNotFoundException(ServiceNotFoundException serviceNotFoundException){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                serviceNotFoundException.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiErrorResponse);

    }
}

