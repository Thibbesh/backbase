package com.backbase.game.kalah.exception;

/**
 *
 */
public class IllegalMoveException extends RuntimeException {

    private static final long serialVersionUID = -3407336632879524317L;

    /**
     *
     * @param message
     */
    public IllegalMoveException(final String message) {
        super("Illegal move: " + message);
    }

}
