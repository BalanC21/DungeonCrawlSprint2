package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private final String name;
    private int gameId;
    private Date savedAt;
    private String currentMap;
    private int playerId;
    private List<String> discoveredMaps = new ArrayList<>();

//    private Key
//private List<Enemy> enemyList;

    public GameState(String currentMap, Date savedAt, String name) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.name = name;
    }
    public GameState(String currentMap, Date savedAt, String name, int gameId, int playerId) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.name = name;
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
