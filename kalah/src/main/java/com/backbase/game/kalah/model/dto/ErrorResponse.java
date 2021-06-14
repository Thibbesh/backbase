package com.backbase.game.kalah.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "path"})
public class ErrorResponse {

    @JsonProperty
    private String message;

    @JsonProperty
    private String path;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
    }
}
