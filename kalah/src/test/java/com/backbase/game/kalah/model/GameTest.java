package com.backbase.game.kalah.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTest {

    @Test
    public void testInitialization() {
        final Game givenGame = new Game();

        Assert.assertNotNull(givenGame.getBoard());
        Assert.assertNull(givenGame.getWhoseTurn());
        Assert.assertNull(givenGame.getWinnerOfTheGame());
    }

    @Test
    public void test_Winner_Of_The_Game() {
        final Game givenGame = new Game();
        givenGame.setWinnerOfTheGame(Player.PLAYER_1);

        Assert.assertEquals(Player.PLAYER_1, givenGame.getWinnerOfTheGame());

        givenGame.setWinnerOfTheGame(Player.PLAYER_2);

        Assert.assertEquals(Player.PLAYER_2, givenGame.getWinnerOfTheGame());
    }

    @Test
    public void test_Whose_Turn_It_Is() {
        final Game givenGame = new Game();
        givenGame.setWhoseTurn(Player.PLAYER_1);

        Assert.assertEquals(Player.PLAYER_1, givenGame.getWhoseTurn());

        givenGame.setWhoseTurn(Player.PLAYER_2);

        Assert.assertEquals(Player.PLAYER_2, givenGame.getWhoseTurn());
    }
}
