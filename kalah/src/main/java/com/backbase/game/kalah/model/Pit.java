package com.backbase.game.kalah.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pit {

    private final int id;
    private int stoneCount;

    @JsonCreator
    public Pit(@JsonProperty("id") final int id) {
        this.id = id;
        if (!this.isHouse()) {
            this.setStoneCount(6);
        }
    }

    /**
     * isHouse method will return kalah or house of both player.
     * @return boolean house of both player
     */
    public boolean isHouse() {
        return (this.getId() == Player.PLAYER_1.getHouseIndex())
                || (this.getId() == Player.PLAYER_2.getHouseIndex());
    }

    /**
     *
     * @return
     */
    public Player getOwner() {
        if (this.getId() <= Player.PLAYER_1.getHouseIndex()) {
            return Player.PLAYER_1;
        } else {
            return Player.PLAYER_2;
        }
    }

    /**
     *
     * @param turn
     * @return
     */
    public boolean isDistributable(final Player turn) {
        return (!turn.equals(Player.PLAYER_1)
                || (this.getId() != Player.PLAYER_2.getHouseIndex()))
                && (!turn.equals(Player.PLAYER_2)
                || (this.getId() != Player.PLAYER_1.getHouseIndex()));
    }
}
