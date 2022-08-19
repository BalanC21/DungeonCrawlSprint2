package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);

    void update(PlayerModel player);

    PlayerModel playerStatsByPlayerName(String playerName);
}
