package com.backbase.game.kalah.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Each game have a board
 * Its a board entity
 */
public class Board {

    public static final int PIT_START_INDEX = 1;
    public static final int PIT_END_INDEX = 14;

    private final List<Pit> pits;

    public Board() {
        this.pits = new ArrayList<>();
        IntStream.rangeClosed(Board.PIT_START_INDEX, Board.PIT_END_INDEX)
                .forEach(pitId -> pits.add(new Pit(pitId)));
    }

    /**
     * Get Pit based on Index
     * @param index of the pit
     * @return pit
     */
    public Pit getPit(final int index) {
        return this.pits.get((index - 1) % Board.PIT_END_INDEX);
    }

    /**
     * get All pits of the board
     * @return pits as a list
     */
    public List<Pit> getPits() {
        return this.pits;
    }

    /**
     * get StoneCount for a player.
     * @param player payer of the game.
     * @param includeHouse kalah or house
     * @return no of stoneCount
     */
    public int getStoneCount(final Player player, final boolean includeHouse) {
        return this.getPits().stream()
                .filter(pit -> (pit.getOwner().equals(player) && (includeHouse || !pit.isHouse())))
                .mapToInt(Pit::getStoneCount).sum();
    }
}
