package com.backbase.game.kalah.exception;

/**
 * Exception handler for GameNotFoundException
 */
public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2L;

    /**
     * End user try to access game which is does not exist.
     * @param id of the game
     */
    public GameNotFoundException(final String id) {
        super("Game not found for id " + id);
    }
}
