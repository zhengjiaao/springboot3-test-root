package com.zja.battleship.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:31
 */
@Getter
@Setter
public class Player {
    private final String name;
    private Board board;
    private boolean ready = false;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }
}