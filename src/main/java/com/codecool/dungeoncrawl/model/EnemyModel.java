package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.lang.reflect.Type;
import java.util.List;

public class EnemyModel extends BaseModel {
    private int hp;
    private int x;
    private int y;

    private Enemy enemy;

    public EnemyModel(Enemy enemy) {
        this.enemy = enemy;
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.hp = enemy.getHealth();
    }

    public int getHp() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getEnemyType(){
        return enemy.getCellType();

    }
}
