package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(GameState state);
    void update(GameState state);
    GameState get(int id);

    String getSaveNameGameState(String name);
    List<GameState> getAll();

    String getMap(String saveName);
}
