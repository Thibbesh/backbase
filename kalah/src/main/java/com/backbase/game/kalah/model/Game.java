package com.backbase.game.kalah.model;

import lombok.Data;

import java.util.UUID;

/**
 * Game is main entity. does contain
 * gameId
 * Board
 * WinnerOfTheGame
 * whoseTurn
 */
@Data
public class Game {

    private final String id;
    private final Board board;
    private Player winnerOfTheGame;
    private Player whoseTurn;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.board = new Board();
    }


}
