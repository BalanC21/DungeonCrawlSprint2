package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface EnemyDao {
    void add(EnemyModel enemy, String playerName);

    void update(EnemyModel enemy);

    public List<EnemyModel> getAll(String saveName);
}
