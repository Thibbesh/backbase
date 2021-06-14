package com.backbase.game.kalah.exception;

import com.backbase.game.kalah.model.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * ExceptionHandling is very important in REST API
 * GameExceptionHandler is ControllerAdvice,
 * its a global exception handling for game REST Api.
 * GameExceptionHandler have covered
 * <p>GameNotFoundException</p>
 * <p>IllegalMoveException</p>
 * <p>GameException</p>
 * <p>GameOverException</p>
 */
@ControllerAdvice
public class GameExceptionHandler {
    private static final String detailsFormat = "[Error] [Message: %s] [Ip: %s] [Path: %s]";
    private Logger logger = LoggerFactory.getLogger(GameExceptionHandler.class);

    /**
     * Global Exception handler for GameNotFoundException
     * @param ex GameNotFoundException
     * @return message of GameNotFoundException
     */
    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String gameNotFoundHandler(final GameNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Its a validation exception, user can notice with Illegal actions
     * @param ex of IllegalMoveException
     * @return message of IllegalMoveException
     */
    @ResponseBody
    @ExceptionHandler(IllegalMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String illegalMoveHandler(final IllegalMoveException ex) {
        return ex.getMessage();
    }


    /**
     * Any internal server exception will handle by handleAllExceptions
     * ArrayOutOfBoundException, wrong pit id, wrong index and so on..
     *
     * @param exception as exception
     * @param request as HttpServletRequest
     * @return ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception exception, HttpServletRequest request) {
        if (exception instanceof GameException)
            logger.warn(String.format(detailsFormat, exception.getMessage(), request.getRemoteHost(), request.getRequestURI()));
        else logger.error(exception.getMessage(), exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(
                "Wrong pit index, It should be 1 to 6 or 8 to 13.",
                request.getRequestURI()), httpStatus);
    }

    /**
     * To indicate end user if game is over or winnerOfTheGame,
     * dont have specific end points.
     * So, if game is over will throw this exception and winnerOfTheGame
     *
     * @param ex of GameOverException
     * @return ErrorResponse
     */
    @ExceptionHandler(GameOverException.class)
    public ResponseEntity<ErrorResponse> gameOverException(final GameOverException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(
                ex.getMessage()),httpStatus);

    }

}
