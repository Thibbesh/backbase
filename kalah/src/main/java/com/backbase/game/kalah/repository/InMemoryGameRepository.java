package com.backbase.game.kalah.repository;

import com.backbase.game.kalah.exception.GameNotFoundException;
import com.backbase.game.kalah.model.Game;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Service
public class InMemoryGameRepository implements GameRepository {

    private final ConcurrentHashMap<String, Game> dataStorage = new ConcurrentHashMap<>();

    /**
     *
     * @param id
     * @return
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
     *
     * @param game
     * @return
     */
    @Override
    public Game save(Game game) {
        this.dataStorage.put(game.getId(), game);
        return this.find(game.getId());
    }
}
