package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.model.EnemyModel;

import java.util.List;

public interface EnemiesDao {


    void add(List<EnemyModel> enemies, int gameStateId);

    void update(EnemyModel enemy);

}
