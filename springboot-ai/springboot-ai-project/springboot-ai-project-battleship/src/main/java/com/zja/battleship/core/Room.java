package com.zja.battleship.core;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:31
 */
@Getter
public class Room {
    private final String roomId;
    private final List<Player> players = new CopyOnWriteArrayList<>();

    public Room(String roomId) {
        this.roomId = roomId;
    }

    public void addPlayer(Player player) {
        if (players.size() < 2) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public int getPlayerCount() {
        return players.size();
    }

    public boolean isFull() {
        return players.size() >= 2;
    }

    public boolean allPlayersReady() {
        return players.size() == 2 &&
                players.get(0).isReady() &&
                players.get(1).isReady();
    }
}