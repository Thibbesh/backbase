package com.backbase.game.kalah.service;

import com.backbase.game.kalah.model.Game;

public interface GameService {

    Game createGame();

    Game play(String gameId, Integer pitId);
}
