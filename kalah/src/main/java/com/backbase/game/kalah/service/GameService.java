package com.backbase.game.kalah.service;

import com.backbase.game.kalah.model.Game;

/**
 * GameService is service layer to build gaming logic.
 */
public interface GameService {

    /**
     * This interface to used createGame
     * @return Game object
     */
    Game createGame();

    /**
     * Move is main interface to play a game.
     *
     * @param gameId id of the Game
     * @param pitId id of the pit
     * @return game after make a move
     */
    Game move(String gameId, Integer pitId);
}
