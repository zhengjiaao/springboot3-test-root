package com.zja.battleship.core;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:31
 */
@Getter
@Setter
public class Game {
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player winner;
    private boolean gameOver = false;

    private Map<Player, Boolean> shipsPlaced = new HashMap<>();

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public void markShipPlaced(Player player) {
        shipsPlaced.put(player, true);
    }

    public boolean allShipsPlaced() {
        return shipsPlaced.getOrDefault(player1, false) &&
                shipsPlaced.getOrDefault(player2, false);
    }

    public boolean isPlayerTurn(Player player) {
        return currentPlayer.equals(player);
    }

    public Player getOpponent(Player player) {
        return player.equals(player1) ? player2 : player1;
    }

    public void switchTurn() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }

    public boolean containsPlayer(Player player) {
        return player1.equals(player) || player2.equals(player);
    }
}