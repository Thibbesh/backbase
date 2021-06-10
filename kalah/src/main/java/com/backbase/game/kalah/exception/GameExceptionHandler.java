package com.backbase.game.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ControllerAdvice
public class GameExceptionHandler {

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String gameNotFoundHandler(final GameNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IllegalMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String illegalMoveHandler(final IllegalMoveException ex) {
        return ex.getMessage();
    }

}
