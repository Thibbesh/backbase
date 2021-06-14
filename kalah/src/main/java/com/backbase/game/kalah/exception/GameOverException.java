package com.backbase.game.kalah.exception;

/**
 * GameOverException is indicate to end user game is over and winnerOfTheGame.
 */
public class GameOverException extends GameException {

    public GameOverException(String message) {
        super(message);
    }
}
