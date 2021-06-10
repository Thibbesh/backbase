package com.backbase.game.kalah.service;

import com.backbase.game.kalah.exception.IllegalMoveException;
import com.backbase.game.kalah.model.Board;
import com.backbase.game.kalah.model.Game;
import com.backbase.game.kalah.model.Pit;
import com.backbase.game.kalah.model.Player;
import com.backbase.game.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

    @Autowired
    public GameServiceImpl(final GameRepository repository) {
        this.repository = repository;
    }

    /**
     *
     * @return
     */
    @Override
    public Game createGame() {
        return this.repository.save(new Game());
    }

    /**
     *
     * @param gameId
     * @param pitId
     * @return
     */
    @Override
    public Game play(String gameId, Integer pitId) {
        final Game game = this.repository.find(gameId);
        checkGameOver(game);
        distributeStones(game, pitId);
        //checkGameOver(game);
        return game;
    }

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

    private void checkGameOver(final Game game) {
        final int player1PitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_1, false);
        final int player2PitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_2, false);
        if ((player1PitStoneCount == 0) || (player2PitStoneCount == 0)) {
            final Pit housePlayer1 = game.getBoard().getPit(Player.PLAYER_1.getHouseIndex());
            final Pit housePlayer2 = game.getBoard().getPit(Player.PLAYER_2.getHouseIndex());
            housePlayer1.setStoneCount(housePlayer1.getStoneCount() + player1PitStoneCount);
            housePlayer2.setStoneCount(housePlayer2.getStoneCount() + player2PitStoneCount);
            determineWinner(game);
            resetBoard(game);
        }
    }

    private void determineWinner(final Game game) {
        final int houseNorthStoneCount = game.getBoard().getStoneCount(Player.PLAYER_1, true);
        final int houseSouthStoneCount = game.getBoard().getStoneCount(Player.PLAYER_2, true);
        if (houseNorthStoneCount > houseSouthStoneCount) {
            game.setWinnerOfTheGame(Player.PLAYER_1);
        } else if (houseNorthStoneCount < houseSouthStoneCount) {
            game.setWinnerOfTheGame(Player.PLAYER_2);
        }
    }

    private void resetBoard(final Game game) {
        game.getBoard().getPits().parallelStream()
                .filter(pit -> (Player.PLAYER_1.getHouseIndex() != pit.getId())
                        && (Player.PLAYER_2.getHouseIndex() != pit.getId()))
                .forEach(pit -> pit.setStoneCount(0));
    }
}
