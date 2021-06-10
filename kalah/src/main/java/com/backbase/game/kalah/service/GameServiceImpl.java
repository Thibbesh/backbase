package com.backbase.game.kalah.service;

import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

    @Autowired
    public GameServiceImpl(final GameRepository repository) {
        this.repository = repository;
    }

    /**
     *
     * @return
     */
    @Override
    public Game createGame() {
        return this.repository.save(new Game());
    }

    /**
     *
     * @param gameId
     * @param pitId
     * @return
     */
    @Override
    public Game play(String gameId, Integer pitId) {
        return null;
    }
}
