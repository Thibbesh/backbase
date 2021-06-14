package com.backbase.game.kalah.exception;

/**
 * GameException is the main exception and extend RuntimeException.
 */
public class GameException extends RuntimeException {

    GameException(String message) {
        super(message);
    }
}
