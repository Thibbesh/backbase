package com.backbase.game.kalah.controller;

import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.model.Pit;
import com.backbase.game.kalah.model.dto.Response;
import com.backbase.game.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GameController is the main api endpoints of the Kalah game.
 * this API have main 2 endpoints,
 * <p>CreateGame</p>
 * <p>MakeAMove</p>
 */
@RestController
public class GameController {

    private final GameService service;
    private final Environment environment;

    @Autowired
    public GameController(final GameService service, final Environment environment) {
        this.service = service;
        this.environment = environment;
    }

    /**
     * REST endpoint to create Game,
     * Its a post method and nothing body.
     *
     * @return Response as ResponseEntity
     */
    @PostMapping("/games")
    public ResponseEntity<Response> createGame() {
        final Game game = this.service.createGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(game.getId(), getUrl(game.getId())));
    }

    /**
     * REST endpoint to play game,
     * This endpoint is the main to play game,
     * End user just put message with gameId and pitId irrespective of players.
     * Business logic will determine whose turn, who is the winner of the game, and so on.
     *
     * @param gameId id of game
     * @param pitId id of pit
     * @return Response as ResponseEntity
     */
    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<Response> makeAMove(@PathVariable final String gameId, @PathVariable final Integer pitId) {
        final Game game = this.service.move(gameId, pitId);
        final Map<Integer, String> gameStatus = game.getBoard().getPits().stream()
                .collect(Collectors.toMap(Pit::getId, value -> Integer.toString(value.getStoneCount())));
        Response response = new Response(game.getId(), getUrl(game.getId()), gameStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Based on gameId will populate url.
     * This URl can send as response.
     *
     * @param gameId id of the game.
     * @return url as string
     */
    private String getUrl(final String gameId) {
        final int port = environment.getProperty("server.port", Integer.class, 8080);
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(),
                port, gameId);
    }

}
