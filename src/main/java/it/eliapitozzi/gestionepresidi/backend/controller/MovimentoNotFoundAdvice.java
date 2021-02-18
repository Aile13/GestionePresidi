package it.eliapitozzi.gestionepresidi.backend.controller;

import it.eliapitozzi.gestionepresidi.backend.exception.MovimentoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Elia
 */
@ControllerAdvice
public class MovimentoNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MovimentoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String movimentoNotFoundHandler(MovimentoNotFoundException ex) {
        return ex.getMessage();
    }
}
