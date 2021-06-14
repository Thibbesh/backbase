package com.backbase.game.kalah.service;

import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService service;

    private Game gameCreated;
    private Game gameOverWinnerPlayer2;
    private Game gameOverWinnerPlayer1;
    private Game gamePlayer1MovedFirst;
    private Game gamePlayer2MovedFirst;
    private Game gameTurnPlayer1;
    private Game gameTurnPlayer2;


    @Before
    public void initDataSetUp()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.gameCreated = new Game();
        this.gameOverWinnerPlayer2 = new Game();
        this.gameOverWinnerPlayer1 = new Game();
        this.gamePlayer1MovedFirst = new Game();
        this.gamePlayer2MovedFirst = new Game();
        this.gameTurnPlayer1 = new Game();
        this.gameTurnPlayer2 = new Game();

        final Method resetBoard =
                methodForTest(service.getClass().getDeclaredMethod("resetKalahBoard", Game.class));
        final Method distributeStones = methodForTest(
                service.getClass().getDeclaredMethod("distributeStones", Game.class, int.class));

        resetBoard.invoke(this.service, this.gameOverWinnerPlayer2);
        this.gameOverWinnerPlayer2.getBoard().getPit(Player.PLAYER_1.getHouseIndex())
                .setStoneCount(10);
        this.gameOverWinnerPlayer2.getBoard().getPit(Player.PLAYER_2.getHouseIndex())
                .setStoneCount(62);
        resetBoard.invoke(this.service, this.gameOverWinnerPlayer1);
        this.gameOverWinnerPlayer1.getBoard().getPit(1).setStoneCount(1);
        this.gameOverWinnerPlayer1.getBoard().getPit(Player.PLAYER_1.getHouseIndex())
                .setStoneCount(39);
        this.gameOverWinnerPlayer1.getBoard().getPit(Player.PLAYER_2.getHouseIndex())
                .setStoneCount(32);

        distributeStones.invoke(this.service, this.gamePlayer1MovedFirst, 1);
        distributeStones.invoke(this.service, this.gamePlayer2MovedFirst, 10);
        distributeStones.invoke(this.service, this.gameTurnPlayer1, 1);
        distributeStones.invoke(this.service, this.gameTurnPlayer2, 8);
    }

    private Method methodForTest(final Method method) {
        ReflectionUtils.makeAccessible(method);
        return method;
    }


    @Test
    @DirtiesContext
    public void testIsGameCreated() {
        final Game game = this.service.createGame();

        Assert.assertNotNull(game);
    }

    @Test
    @DirtiesContext
    public void test_Move_Stones() {
        final Game game = this.service.createGame();
        this.service.move(game.getId(), 6);

        Assert.assertEquals(Player.PLAYER_2, game.getWhoseTurn());
        Assert.assertNull(game.getWinnerOfTheGame());
        Assert.assertEquals(31, game.getBoard().getStoneCount(Player.PLAYER_1, true));
    }

    @Test
    public void test_Decide_Whose_Turn() {
        Assert.assertNull(this.gameCreated.getWhoseTurn());
        Assert.assertEquals(Player.PLAYER_1, this.gamePlayer1MovedFirst.getWhoseTurn());
        Assert.assertEquals(Player.PLAYER_1, this.gamePlayer2MovedFirst.getWhoseTurn());
    }

    @Test
    public void test_Winner_Of_The_Game()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method determineWinner =
                methodForTest(service.getClass().getDeclaredMethod("determineWinnerOfTheGame", Game.class));
        determineWinner.invoke(this.service, this.gameCreated);
        determineWinner.invoke(this.service, this.gameOverWinnerPlayer2);
        determineWinner.invoke(this.service, this.gameOverWinnerPlayer1);

        Assert.assertNull(this.gameCreated.getWinnerOfTheGame());
        Assert.assertEquals(Player.PLAYER_2, this.gameOverWinnerPlayer2.getWinnerOfTheGame());
        Assert.assertEquals(Player.PLAYER_1, this.gameOverWinnerPlayer1.getWinnerOfTheGame());
    }
}
