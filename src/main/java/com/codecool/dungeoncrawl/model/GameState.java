package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Enemy;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private final String name;
    private Date savedAt;
    private String currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;

//    private Key
//private List<Enemy> enemyList;

    public GameState(String currentMap, Date savedAt, PlayerModel player, String name ) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
        this.name = name;
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

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
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
