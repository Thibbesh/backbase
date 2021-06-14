package com.backbase.game.kalah.service;

import com.backbase.game.kalah.exception.GameOverException;
import com.backbase.game.kalah.exception.IllegalMoveException;
import com.backbase.game.kalah.model.Board;
import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.model.Pit;
import com.backbase.game.kalah.model.Player;
import com.backbase.game.kalah.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GameServiceImpl is implementation class of GameService
 * GameServiceImpl resides main Gaming logic which are mentioned below
 * <p>distributeStones </p>
 * <p>validateMove</p>
 * <p>decideWhoseTurn</p>
 * <p>lastEmptyPit</p>
 * <p>checkGameOver</p>
 * <p>determineWinnerOfTheGame</p>
 * <p>ResetBoard</p>
 *
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);


    private final GameRepository repository;

    @Autowired
    public GameServiceImpl(final GameRepository repository) {
        this.repository = repository;
    }

    /**
     * Creating game
     * @return game of saved entity
     */
    @Override
    public Game createGame() {
        return this.repository.save(new Game());
    }

    /**
     * Move is the main business logic or gaming logic.
     * It does mainly,
     * Stones distribution
     * validate move
     * decide whose turn
     * last empty pit
     * checkGameOver
     * determineWinnerOfTheGame
     * reset Kalah board
     *
     * @param gameId id of the game
     * @param pitId id of the pit
     * @return game after make a move
     */
    @Override
    public Game move(String gameId, Integer pitId) {

        final Game game = this.repository.find(gameId);
        if (game.getWinnerOfTheGame()!= null) {
            logger.info("Game is over and winner of the game is : " + game.getWinnerOfTheGame());
            throw new GameOverException("Game is over and winner of the game is : " + game.getWinnerOfTheGame());
        }
        distributeStones(game, pitId);
        checkGameOver(game);
        return game;
    }

    /**
     * distribute Stone starting from @pitId to right
     * @param game current game
     * @param pitId is of the pit
     */
    private void distributeStones(final Game game, int pitId) {
        final Pit startPit = game.getBoard().getPit(pitId);
        validateMove(game, pitId);
        int stoneToDistribute = startPit.getStoneCount();
        startPit.setStoneCount(0);
        while (stoneToDistribute > 0) {
            final Pit currentPit = game.getBoard().getPit(++pitId);
            if (currentPit.isDistributable(game.getWhoseTurn())) {
                currentPit.setStoneCount(currentPit.getStoneCount() + 1);
                stoneToDistribute--;
            }
        }
        lastEmptyPit(game, pitId);
        decideWhoseTurn(game, pitId);
    }

    /**
     * validation or rules check whose turn, pit is house or empty.
     * @param game current game
     * @param startPitId starting pit Id
     */
    private void validateMove(final Game game, final int startPitId) {
        final Pit startPit = game.getBoard().getPit(startPitId);
        if (startPit.isHouse()) {
            throw new IllegalMoveException("It's house/kalah, Can not start from house/kalah");
        }
        if (Player.PLAYER_1.equals(game.getWhoseTurn())
                && !Player.PLAYER_1.equals(startPit.getOwner())) {
            throw new IllegalMoveException("It's Player 1 turn");
        }
        if (Player.PLAYER_2.equals(game.getWhoseTurn())
                && !Player.PLAYER_2.equals(startPit.getOwner())) {
            throw new IllegalMoveException("It's Player 2 turn");
        }
        if (startPit.getStoneCount() == 0) {
            throw new IllegalMoveException("pit is empty, Can not start from empty pit");
        }
        if (game.getWhoseTurn() == null) {
            if (Player.PLAYER_1.equals(startPit.getOwner())) {
                game.setWhoseTurn(Player.PLAYER_1);
            } else {
                game.setWhoseTurn(Player.PLAYER_2);
            }
        }
    }

    /**
     * Rules to decide whoseTurn
     * @param game current game
     * @param pitId id of pit
     */
    private void decideWhoseTurn(final Game game, final int pitId) {
        final Pit pit = game.getBoard().getPit(pitId);
        if (pit.isHouse() && Player.PLAYER_1.equals(pit.getOwner())
                && Player.PLAYER_1.equals(game.getWhoseTurn())) {
            game.setWhoseTurn(Player.PLAYER_1);
        } else if (pit.isHouse() && Player.PLAYER_2.equals(pit.getOwner())
                && Player.PLAYER_2.equals(game.getWhoseTurn())) {
            game.setWhoseTurn(Player.PLAYER_2);
        } else {
            if (Player.PLAYER_1.equals(game.getWhoseTurn())) {
                game.setWhoseTurn(Player.PLAYER_2);
            } else {
                game.setWhoseTurn(Player.PLAYER_1);
            }
        }
    }

    /**
     * Rules to make empty pit
     * @param game current game
     * @param endPitId end of pit iD
     */
    private void lastEmptyPit(final Game game, final int endPitId) {
        final Pit endPit = game.getBoard().getPit(endPitId);
        if (!endPit.isHouse() && endPit.getOwner().equals(game.getWhoseTurn())
                && (endPit.getStoneCount() == 1)) {
            final Pit oppositePit = game.getBoard().getPit(Board.PIT_END_INDEX - endPit.getId());
            if (oppositePit.getStoneCount() > 0) {
                final Pit house = game.getBoard().getPit(endPit.getOwner().getHouseIndex());
                house.setStoneCount(
                        (house.getStoneCount() + oppositePit.getStoneCount()) + endPit.getStoneCount());
                oppositePit.setStoneCount(0);
                endPit.setStoneCount(0);
            }
        }
    }

    /**
     * Rules to Check weather game is over.
     * @param game current game
     */
    private void checkGameOver(final Game game) {
        final int player1PitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_1, false);
        final int player2PitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_2, false);
         if ((player1PitStoneCount == 0) || (player2PitStoneCount == 0)) {
            final Pit housePlayer1 = game.getBoard().getPit(Player.PLAYER_1.getHouseIndex());
            final Pit housePlayer2 = game.getBoard().getPit(Player.PLAYER_2.getHouseIndex());
            housePlayer1.setStoneCount(housePlayer1.getStoneCount() + player1PitStoneCount);
            housePlayer2.setStoneCount(housePlayer2.getStoneCount() + player2PitStoneCount);
            determineWinnerOfTheGame(game);
            resetKalahBoard(game);
        }
     }

    /**
     * Rules to decide who is the winner of the game.
     * @param game current game
     */
    private void determineWinnerOfTheGame(final Game game) {
        final int housePlayer1StoneCount = game.getBoard().getStoneCount(Player.PLAYER_1, true);
        final int housePlayer2StoneCount = game.getBoard().getStoneCount(Player.PLAYER_2, true);
        if (housePlayer1StoneCount > housePlayer2StoneCount) {
            game.setWinnerOfTheGame(Player.PLAYER_1);
        } else if (housePlayer1StoneCount < housePlayer2StoneCount) {
            game.setWinnerOfTheGame(Player.PLAYER_2);
        }
    }

    /**
     * rule to set stone count to zero.
     * @param game current game
     */
    private void resetKalahBoard(final Game game) {
        game.getBoard().getPits().parallelStream()
                .filter(pit -> (Player.PLAYER_1.getHouseIndex() != pit.getId())
                        && (Player.PLAYER_2.getHouseIndex() != pit.getId()))
                .forEach(pit -> pit.setStoneCount(0));
    }
}
