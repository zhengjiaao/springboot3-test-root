package com.zja.battleship.core;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:32
 */
@Service
public class GameService {

    private final Map<String, Game> gameMap = new HashMap<>();
    private final Map<String, Game> playerGameMap = new HashMap<>();

    public void startGame(Room room) {
        if (room.getPlayerCount() < 2) return;

        Player player1 = room.getPlayers().get(0);
        Player player2 = room.getPlayers().get(1);

        Game game = new Game(player1, player2);
        gameMap.put(room.getRoomId(), game);
        playerGameMap.put(player1.getName(), game);
        playerGameMap.put(player2.getName(), game);
    }

    public void placeShip(Player player, ShipType shipType, int startX, int startY, boolean horizontal) {
        Game game = getGameByPlayer(player);
        if (game != null) {
            Board board = player.getBoard();
            Ship ship = new Ship(shipType);
            if (board.placeShip(ship, startX, startY, horizontal)) {
                game.markShipPlaced(player);
            }
        }
    }

    public Board.AttackResult attack(Player attacker, int x, int y) {
        Game game = getGameByPlayer(attacker);
        if (game != null && game.isPlayerTurn(attacker)) {
            Player opponent = game.getOpponent(attacker);
            Board opponentBoard = opponent.getBoard();
            Board.AttackResult result = opponentBoard.attack(x, y);

            if (result == Board.AttackResult.HIT || result == Board.AttackResult.SUNK) {
                // 击中目标，继续攻击
            } else {
                // 未击中，切换回合
                game.switchTurn();
            }

            if (result == Board.AttackResult.GAME_OVER) {
                game.setGameOver(true);
                game.setWinner(attacker);
            }

            return result;
        }
        return Board.AttackResult.INVALID;
    }

    public Game getGameByPlayer(Player player) {
        return playerGameMap.get(player.getName());
    }

    public Game getGameByRoomId(String roomId) {
        return gameMap.get(roomId);
    }
}