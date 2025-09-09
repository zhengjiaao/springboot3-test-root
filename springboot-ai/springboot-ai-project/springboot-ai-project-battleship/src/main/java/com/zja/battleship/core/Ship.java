package com.zja.battleship.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:32
 */
public class Ship {
    private final ShipType type;
    private final List<Position> positions = new ArrayList<>();
    private boolean isHorizontal;

    public Ship(ShipType type) {
        this.type = type;
    }

    public ShipType getType() {
        return type;
    }

    public void setPosition(int startX, int startY, boolean isHorizontal) {
        positions.clear();
        this.isHorizontal = isHorizontal;
        for (int i = 0; i < type.getSize(); i++) {
            if (isHorizontal) {
                positions.add(new Position(startX, startY + i));
            } else {
                positions.add(new Position(startX + i, startY));
            }
        }
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void hit() {
        // 实现击中逻辑，可以根据需要添加具体实现
    }

    public boolean isSunk() {
        // 实现判断船是否被击沉的逻辑，可以根据需要添加具体实现
        return false;
    }
}

class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}