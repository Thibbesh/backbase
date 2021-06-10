package com.backbase.game.kalah.model;

/**
 *
 */
public enum Player {

    PLAYER_1(Board.PIT_END_INDEX / 2),
    PLAYER_2(Board.PIT_END_INDEX);

    private int houseIndex;

    Player(final int houseIndex) {
        this.houseIndex = houseIndex;
    }

    public int getHouseIndex() {
        return this.houseIndex;
    }

}
