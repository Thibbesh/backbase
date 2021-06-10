package com.backbase.game.kalah.controller;

import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.model.dto.Response;
import com.backbase.game.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

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
     * @return
     */
    private String getUrl(final String gameId) {
        final int port = environment.getProperty("server.port", Integer.class, 8080);
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(),
                port, gameId);
    }

}
