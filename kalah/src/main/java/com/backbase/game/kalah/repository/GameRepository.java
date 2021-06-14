package com.backbase.game.kalah.repository;

import com.backbase.game.kalah.model.Game;

/**
 * Its a Repository for the game.
 * For this game we have only two operations
 * <p>find</p>
 * <p>Save</p>
 */
public interface GameRepository {

    /**
     * Find the game form repository
     *
     * @param id of the game
     * @return game of given Id
     */
    Game find(final String id);

    /**
     * to save game object in the repository.
     * @param game to be saved
     * @return gave
     */
    Game save(final Game game);

}
