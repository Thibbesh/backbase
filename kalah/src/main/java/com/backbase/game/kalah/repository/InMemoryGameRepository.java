package com.backbase.game.kalah.repository;

import com.backbase.game.kalah.exception.GameNotFoundException;
import com.backbase.game.kalah.model.Game;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Its a in memory Database for Kalah/Game.
 * ConcurrentHashMap is dataStorage/database for the game,
 * This collection context is available application level.
 */
@Service
public class InMemoryGameRepository implements GameRepository {

    private final ConcurrentHashMap<String, Game> dataStorage = new ConcurrentHashMap<>();

    /**
     * Find game based on id in HashMap collection.
     * @param id of the game
     * @return game of given Id
     */
    @Override
    public Game find(String id) {
        final Game game = this.dataStorage.get(id);
        if (game == null) {
            throw new GameNotFoundException(id);
        }
        return game;
    }

    /**
     * Save Game entity to Hash collection.
     *
     * @param game object to be saved
     * @return Game of saved entity
     */
    @Override
    public Game save(Game game) {
        this.dataStorage.put(game.getId(), game);
        return this.find(game.getId());
    }
}
