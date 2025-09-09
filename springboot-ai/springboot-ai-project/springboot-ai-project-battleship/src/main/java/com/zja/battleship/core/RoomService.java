package com.zja.battleship.core;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:49
 */
@Service
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room createRoom() {
        String roomId = generateRoomId();
        Room room = new Room(roomId);
        rooms.put(roomId, room);
        return room;
    }

    public Room joinRoom(String roomId, Player player) {
        Room room = rooms.get(roomId);
        if (room != null && !room.isFull()) {
            room.addPlayer(player);
            return room;
        }
        return null;
    }

    public Room getRoomByPlayer(Player player) {
        for (Room room : rooms.values()) {
            if (room.containsPlayer(player)) {
                return room;
            }
        }
        return null;
    }

    private String generateRoomId() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}