package com.backbase.game.kalah.exception;

/**
 * Its validation Exception
 * <p>WhoseTurn</p>
 * <p>house/kalah</p>
 * <p>pit is empty</p>
 */
public class IllegalMoveException extends RuntimeException {

    private static final long serialVersionUID = -3L;

    /**
     *
     * @param message of invalid operations
     */
    public IllegalMoveException(final String message) {
        super("Illegal move: " + message);
    }

}
