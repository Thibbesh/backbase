package com.backbase.game.kalah.repository;

import com.backbase.game.kalah.model.Game;

/**
 *
 */
public interface GameRepository {

    Game find(final String id);

    Game save(final Game game);

}
