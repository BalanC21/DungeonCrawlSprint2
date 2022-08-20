package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface GameStateDao {
    void add(GameState state, int selectedPlayerId);
    void update(GameState state);
    GameState get(String name);
    boolean checkIfSavedInstanceExists(String input);

    int getPlayerId(String name);
}
