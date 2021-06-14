package com.backbase.game.kalah.model;

/**
 * For this game we have 2 player
 * Player 1 and player 2.
 * PitIndex of the Player 1 is 1 to 7
 * PitIndex of the Player 2 is 8 to 13
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
