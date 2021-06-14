package com.backbase.game.kalah.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PitTest {

    @Test
    public void testDistributable() {
        final Pit pit1 = new Pit(1);
        final Pit pit2 = new Pit(14);
        final Pit pit3 = new Pit(7);

        Assert.assertTrue(pit1.isDistributable(Player.PLAYER_1));
        Assert.assertTrue(pit2.isDistributable(Player.PLAYER_2));
        Assert.assertFalse(pit2.isDistributable(Player.PLAYER_1));
        Assert.assertFalse(pit3.isDistributable(Player.PLAYER_2));
    }

    @Test
    public void test_its_House_Kalah() {
        final Pit pit1 = new Pit(7);
        final Pit pit2 = new Pit(14);
        final Pit givenPit3 = new Pit(3);
        final Pit givenPit4 = new Pit(9);

        Assert.assertTrue(pit1.isHouse());
        Assert.assertTrue(pit2.isHouse());
        Assert.assertFalse(givenPit3.isHouse());
        Assert.assertFalse(givenPit4.isHouse());
    }

    @Test
    public void testInitialization() {
        final Pit givenPit1 = new Pit(1);
        final Pit givenPit2 = new Pit(14);
        final Pit givenPit3 = new Pit(7);

        Assert.assertEquals(1, givenPit1.getId());
        Assert.assertEquals(14, givenPit2.getId());
        Assert.assertEquals(7, givenPit3.getId());
    }

    @Test
    public void test_Initial_Stone_Count() {
        final Pit givenPit1 = new Pit(1);
        final Pit givenPit2 = new Pit(7);

        Assert.assertEquals(6, givenPit1.getStoneCount());
        Assert.assertEquals(0, givenPit2.getStoneCount());
    }

    @Test
    public void test_Who_Is_The_Owner() {
        final Pit givenPit1 = new Pit(4);
        final Pit givenPit2 = new Pit(7);
        final Pit givenPit3 = new Pit(10);
        final Pit givenPit4 = new Pit(14);

        Assert.assertEquals(Player.PLAYER_1, givenPit1.getOwner());
        Assert.assertEquals(Player.PLAYER_1, givenPit2.getOwner());
        Assert.assertEquals(Player.PLAYER_2, givenPit3.getOwner());
        Assert.assertEquals(Player.PLAYER_2, givenPit4.getOwner());
    }

    @Test
    public void testStoneCountSet() {
        final Pit givenPit = new Pit(1);
        givenPit.setStoneCount(7);

        Assert.assertEquals(7, givenPit.getStoneCount());
    }
}
