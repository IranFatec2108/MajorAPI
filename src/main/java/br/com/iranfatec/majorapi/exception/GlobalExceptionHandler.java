package br.com.iranfatec.majorapi.exception;

import br.com.iranfatec.majorapi.servico.NomeAtivoJaExistenteException;
import br.com.iranfatec.majorapi.servico.ServicoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NomeAtivoJaExistenteException.class)
    public ResponseEntity<ApiErrorResponse> nomeAtivoJaExistenteException(NomeAtivoJaExistenteException exception) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> requestInvalidoException(MethodArgumentNotValidException methodArgumentNotValidException) {

        String mensagemErro = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                mensagemErro
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorResponse);
    }

    @ExceptionHandler(ServicoNaoEncontradoException.class)
    public ResponseEntity <ApiErrorResponse> servicoNaoEncontradoException(ServicoNaoEncontradoException servicoNaoEncontradoException){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                servicoNaoEncontradoException.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiErrorResponse);

    }
}

