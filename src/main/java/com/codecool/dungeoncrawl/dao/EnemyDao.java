package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface EnemyDao {
    void add(EnemyModel enemy);

    void update(EnemyModel enemy);

    EnemyModel get(int id);

    List<EnemyModel> getAll();
}
