package com.codecool.dungeoncrawl.model;
import java.sql.Date;

public class GameState extends BaseModel {
    private final String name;
    private final Date savedAt;
    private final String currentMap;
    private PlayerModel player;

    public GameState(String currentMap, Date savedAt, PlayerModel player, String name) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
        this.name = name;
    }

    public Date getSavedAt() {
        return savedAt;
    }
    public String getCurrentMap() {
        return currentMap;
    }
    public PlayerModel getPlayer() {
        return player;
    }
    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }
}
