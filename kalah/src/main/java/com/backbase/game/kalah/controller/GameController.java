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
 *
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
     *
     * @return
     */
    @PostMapping("/games")
    public ResponseEntity<Response> createGame() {
        final Game game = this.service.createGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(game.getId(), getUrl(game.getId())));
    }

    /**
     *
     * @param gameId
     * @param pitId
     * @return
     */
    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<Response> playGame(@PathVariable final String gameId, @PathVariable final Integer pitId) {
        final Game game = this.service.play(gameId, pitId);
        final Map<Integer, String> status = game.getBoard().getPits().stream()
                .collect(Collectors.toMap(Pit::getId, value -> Integer.toString(value.getStoneCount())));
        Response response = new Response(game.getId(), getUrl(game.getId()), status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     *
     * @param gameId
     * @return
     */
    private String getUrl(final String gameId) {
        final int port = environment.getProperty("server.port", Integer.class, 8080);
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(),
                port, gameId);
    }

}
