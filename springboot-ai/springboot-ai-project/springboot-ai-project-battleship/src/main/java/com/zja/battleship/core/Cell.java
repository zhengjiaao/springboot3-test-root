package com.zja.battleship.core;

import lombok.Getter;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:32
 */
@Getter
public class Cell {
    private final int x;
    private final int y;
    private Ship ship;
    private boolean hit;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
}