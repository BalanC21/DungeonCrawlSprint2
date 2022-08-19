package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(GameState state);

    String getSaveNameGameState(String name);

    String getMap(String saveName);
}
