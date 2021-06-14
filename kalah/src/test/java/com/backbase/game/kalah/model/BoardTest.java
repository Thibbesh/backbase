package com.backbase.game.kalah.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardTest {

    @Test
    public void testGetPit_Based_On_Index() {
        final Board givenBoard = new Board();
        final Pit givenPit = givenBoard.getPit(4);

        Assert.assertNotNull(givenPit);
        Assert.assertEquals(4, givenPit.getId());
    }

    @Test
    public void test_Initialization_Board() {
        final Board givenBoard = new Board();

        Assert.assertNotNull(givenBoard.getPits());
        Assert.assertEquals(Board.PIT_END_INDEX, givenBoard.getPits().size());
    }

    @Test
    public void test_StoneCount() {
        final Board givenBoard1 = new Board();
        final Board givenBoard2 = new Board();
        givenBoard2.getPit(5).setStoneCount(0);
        givenBoard2.getPit(11).setStoneCount(9);

        Assert.assertEquals(36, givenBoard1.getStoneCount(Player.PLAYER_1, true));
        Assert.assertEquals(30, givenBoard2.getStoneCount(Player.PLAYER_1, true));
        Assert.assertEquals(39, givenBoard2.getStoneCount(Player.PLAYER_2, true));
    }
}
