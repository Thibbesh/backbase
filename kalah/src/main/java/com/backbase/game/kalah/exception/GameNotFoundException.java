package com.backbase.game.kalah.exception;

/**
 *
 */
public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2576221153177453295L;

    /**
     *
     * @param id
     */
    public GameNotFoundException(final String id) {
        super("Game not found for id " + id);
    }
}
