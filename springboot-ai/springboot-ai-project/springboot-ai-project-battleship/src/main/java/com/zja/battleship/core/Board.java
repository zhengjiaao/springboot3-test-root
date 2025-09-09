package com.zja.battleship.core;

import lombok.Getter;
import com.zja.battleship.core.Cell;
import com.zja.battleship.core.Ship;
import java.util.List;

import java.util.ArrayList;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:32
 */
@Getter
public class Board {
    public static final int SIZE = 10;
    private final Cell[][] grid = new Cell[SIZE][SIZE];
    private final List<Ship> ships = new ArrayList<>();

    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public boolean placeShip(Ship ship, int startX, int startY, boolean isHorizontal) {
        int length = ship.getType().getSize();

        // 检查边界
        if (isHorizontal) {
            if (startX < 0 || startX >= SIZE || startY < 0 || startY + length > SIZE) {
                return false;
            }
            for (int i = startY; i < startY + length; i++) {
                if (grid[startX][i].hasShip()) return false;
            }
        } else {
            if (startX < 0 || startX + length > SIZE || startY < 0 || startY >= SIZE) {
                return false;
            }
            for (int i = startX; i < startX + length; i++) {
                if (grid[i][startY].hasShip()) return false;
            }
        }

        // 放置船只
        if (isHorizontal) {
            for (int i = startY; i < startY + length; i++) {
                grid[startX][i].setShip(ship);
            }
        } else {
            for (int i = startX; i < startX + length; i++) {
                grid[i][startY].setShip(ship);
            }
        }

        ship.setPosition(startX, startY, isHorizontal);
        ships.add(ship);
        return true;
    }

    public AttackResult attack(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return AttackResult.INVALID;
        }

        Cell cell = grid[x][y];
        if (cell.isHit()) {
            return AttackResult.ALREADY_HIT;
        }

        cell.setHit(true);
        if (cell.hasShip()) {
            Ship ship = cell.getShip();
            ship.hit();
            if (ship.isSunk()) {
                if (allShipsSunk()) {
                    return AttackResult.GAME_OVER;
                }
                return AttackResult.SUNK;
            }
            return AttackResult.HIT;
        }
        return AttackResult.MISS;
    }

    private boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public enum AttackResult {
        INVALID, ALREADY_HIT, MISS, HIT, SUNK, GAME_OVER
    }
}