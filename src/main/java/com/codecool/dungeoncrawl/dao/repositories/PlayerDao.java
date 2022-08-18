package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);

    void update(PlayerModel player);

    public int getDbX(int id);

    public int getDbY(int id);

    public int getDbHp(int id);

    PlayerModel get(int id);

    List<PlayerModel> getAll();

    public int getIdByName(String playerName);

    public String getMap(int id);
    public PlayerModel playerStatsByPlayerName(String playerName);
}
