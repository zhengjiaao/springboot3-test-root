package com.zja.battleship.core;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:32
 */
public enum ShipType {
    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}