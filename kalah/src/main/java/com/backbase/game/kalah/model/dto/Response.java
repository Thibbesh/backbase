package com.backbase.game.kalah.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private final String id;
    private final String uri;
    private final Map<Integer, String> gameStatus;

    public Response(final String id, final String uri) {
        this(id, uri, null);
    }

    public Response(final String id, final String uri, final Map<Integer, String> status) {
        this.id = id;
        this.uri = uri;
        this.gameStatus = status;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Map<Integer, String> getStatus() {
        return gameStatus;
    }

}
