package com.zja.battleship.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zja.battleship.core.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhengja
 * @Date: 2025-07-14 14:29
 */
@Slf4j
@Component
public class GameWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Map<Channel, Player> channelPlayerMap = new ConcurrentHashMap<>();
    private final Map<Player, Channel> playerChannelMap = new ConcurrentHashMap<>();

    @Autowired
    private RoomService roomService;

    @Autowired
    private GameService gameService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String payload = msg.text();
        log.info("Received message: {}", payload);

        JsonNode jsonNode = objectMapper.readTree(payload);
        String action = jsonNode.get("action").asText();

        Player player = channelPlayerMap.get(ctx.channel());

        switch (action) {
            case "JOIN_ROOM":
                handleJoinRoom(ctx, jsonNode);
                break;
            case "PLACE_SHIP":
                handlePlaceShip(player, jsonNode);
                break;
            case "ATTACK":
                handleAttack(player, jsonNode);
                break;
            case "READY":
                handleReady(player);
                break;
            case "CHAT":
                handleChat(player, jsonNode);
                break;
            case "LEAVE_ROOM":
                handleLeaveRoom(player);
                break;
            case "START_GAME":
                handleStartGame(player);
                break;
        }
    }

    private void handleJoinRoom(ChannelHandlerContext ctx, JsonNode data) throws JsonProcessingException {
        String playerName = data.get("playerName").asText();
        String roomId = data.get("roomId").asText();

        Player player = new Player(playerName);
        channelPlayerMap.put(ctx.channel(), player);
        playerChannelMap.put(player, ctx.channel());

        Room room = roomService.joinRoom(roomId, player);
        if (room != null) {
            sendRoomInfo(room);
            if (room.isFull()) {
                sendGameStart(room);
            }
        } else {
            sendError(ctx.channel(), "无法加入房间，可能房间已满或不存在");
        }
    }

    private void handlePlaceShip(Player player, JsonNode data) throws JsonProcessingException {
        ShipType shipType = ShipType.valueOf(data.get("shipType").asText());
        int x = data.get("x").asInt();
        int y = data.get("y").asInt();
        boolean horizontal = data.get("horizontal").asBoolean();

        gameService.placeShip(player, shipType, x, y, horizontal);
        sendPlacementResult(player, true, "船只放置成功");

        Game game = gameService.getGameByPlayer(player);
        if (game != null && game.allShipsPlaced()) {
            sendAllShipsPlaced(game);
        }
    }

    private void handleAttack(Player player, JsonNode data) throws JsonProcessingException {
        int x = data.get("x").asInt();
        int y = data.get("y").asInt();

        GameService.AttackResult result = gameService.attack(player, x, y);
        sendAttackResult(player, x, y, result);

        Game game = gameService.getGameByPlayer(player);
        if (game != null && game.isGameOver()) {
            sendGameOver(game);
        }
    }

    private void handleReady(Player player) throws JsonProcessingException {
        Room room = roomService.getRoomByPlayer(player);
        if (room != null) {
            player.setReady(true);
            sendPlayerReady(room, player);

            if (room.allPlayersReady()) {
                gameService.startGame(room);
                sendGameStart(room);
            }
        }
    }

    private void handleStartGame(Player player) throws JsonProcessingException {
        Room room = roomService.getRoomByPlayer(player);
        if (room != null) {
            gameService.startGame(room);
            sendGameStart(room);
        }
    }

    private void handleChat(Player player, JsonNode data) throws JsonProcessingException {
        String message = data.get("message").asText();
        Room room = roomService.getRoomByPlayer(player);
        if (room != null) {
            broadcastChat(room, player, message);
        }
    }

    private void handleLeaveRoom(Player player) throws JsonProcessingException {
        Room room = roomService.getRoomByPlayer(player);
        if (room != null) {
            room.removePlayer(player);
            sendRoomInfo(room);
        }
    }

    private void sendRoomInfo(Room room) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "ROOM_UPDATE");
        message.put("roomId", room.getRoomId());

        ArrayNode playersNode = message.putArray("players");
        for (Player player : room.getPlayers()) {
            ObjectNode playerNode = playersNode.addObject();
            playerNode.put("name", player.getName());
            playerNode.put("ready", player.isReady());
        }

        message.put("playerCount", room.getPlayerCount());
        message.put("isFull", room.isFull());

        String json = objectMapper.writeValueAsString(message);
        broadcast(room, json);
    }

    private void sendGameStart(Room room) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "GAME_START");
        message.put("roomId", room.getRoomId());

        Game game = gameService.getGameByRoomId(room.getRoomId());
        if (game != null) {
            message.put("currentPlayer", game.getCurrentPlayer().getName());
        }

        String json = objectMapper.writeValueAsString(message);
        broadcast(room, json);
    }

    private void sendAttackResult(Player player, int x, int y, GameService.AttackResult result) throws JsonProcessingException {
        Game game = gameService.getGameByPlayer(player);
        if (game == null) return;

        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "ATTACK_RESULT");
        message.put("x", x);
        message.put("y", y);
        message.put("result", result.name());
        message.put("currentPlayer", game.getCurrentPlayer().getName());

        String json = objectMapper.writeValueAsString(message);

        // 发送给两个玩家
        sendToPlayer(game.getPlayer1(), json);
        sendToPlayer(game.getPlayer2(), json);
    }

    private void sendGameOver(Game game) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "GAME_OVER");
        message.put("winner", game.getWinner().getName());

        String json = objectMapper.writeValueAsString(message);

        // 发送给两个玩家
        sendToPlayer(game.getPlayer1(), json);
        sendToPlayer(game.getPlayer2(), json);
    }

    private void broadcastChat(Room room, Player sender, String text) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "CHAT");
        message.put("sender", sender.getName());
        message.put("message", text);

        String json = objectMapper.writeValueAsString(message);
        broadcast(room, json);
    }

    private void sendPlacementResult(Player player, boolean success, String message) throws JsonProcessingException {
        ObjectNode jsonMessage = objectMapper.createObjectNode();
        jsonMessage.put("type", "PLACEMENT_RESULT");
        jsonMessage.put("success", success);
        jsonMessage.put("message", message);

        sendToPlayer(player, objectMapper.writeValueAsString(jsonMessage));
    }

    private void sendAllShipsPlaced(Game game) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "ALL_SHIPS_PLACED");

        String json = objectMapper.writeValueAsString(message);

        sendToPlayer(game.getPlayer1(), json);
        sendToPlayer(game.getPlayer2(), json);
    }

    private void sendPlayerReady(Room room, Player player) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "PLAYER_READY");
        message.put("player", player.getName());

        String json = objectMapper.writeValueAsString(message);
        broadcast(room, json);
    }

    private void broadcast(Room room, String message) {
        for (Player player : room.getPlayers()) {
            Channel channel = playerChannelMap.get(player);
            if (channel != null && channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame(message));
            }
        }
    }

    private void sendToPlayer(Player player, String message) {
        Channel channel = playerChannelMap.get(player);
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }

    private void sendError(Channel channel, String error) throws JsonProcessingException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("type", "ERROR");
        message.put("message", error);

        String json = objectMapper.writeValueAsString(message);
        channel.writeAndFlush(new TextWebSocketFrame(json));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Player player = channelPlayerMap.remove(ctx.channel());
        if (player != null) {
            playerChannelMap.remove(player);
            Room room = roomService.getRoomByPlayer(player);
            if (room != null) {
                room.removePlayer(player);
                try {
                    sendRoomInfo(room);
                } catch (JsonProcessingException e) {
                    log.error("Error sending room info", e);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("WebSocket error", cause);
        ctx.close();
    }
}