package com.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handlerUnauthorizedError(HttpServletRequest req, Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse();
        response.setMensaje(ex.getMessage());

        //return new ResponseEntity<>(response, HttpStatus.valueOf(response.getMetadata().getStatus()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerGenericError(HttpServletRequest req, Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse();
        response.setMensaje("Ocurrió un error no esperado, por favor comuníquese con el área de soporte.");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
