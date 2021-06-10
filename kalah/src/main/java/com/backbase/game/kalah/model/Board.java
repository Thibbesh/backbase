package com.backbase.game.kalah.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int PIT_START_INDEX = 1;
    public static final int PIT_END_INDEX = 14;

    private final List<Pit> pits;

    public Board() {
        this.pits = new ArrayList<>();
        for (int i = Board.PIT_START_INDEX; i <= Board.PIT_END_INDEX; i++) {
            this.pits.add(new Pit(i));
        }
    }

    /**
     *
     * @param index
     * @return
     */
    public Pit getPit(final int index) {
        return this.pits.get((index - 1) % Board.PIT_END_INDEX);
    }

    /**
     *
     * @return
     */
    public List<Pit> getPits() {
        return this.pits;
    }

    /**
     *
     * @param player
     * @param includeHouse
     * @return
     */
    public int getStoneCount(final Player player, final boolean includeHouse) {
        return this.getPits().stream()
                .filter(pit -> (pit.getOwner().equals(player) && (includeHouse || !pit.isHouse())))
                .mapToInt(Pit::getStoneCount).sum();
    }
}
