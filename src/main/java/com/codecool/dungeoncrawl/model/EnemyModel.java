package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.lang.reflect.Type;
import java.util.List;

public class EnemyModel extends BaseModel {
    List<Enemy> enemies;
    String enemyType;
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

    public EnemyModel(String enemyType, int x, int y, int hp) {
        this.enemyType = enemyType;
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    public EnemyModel(List<Enemy> enemies) {
        this.enemies = enemies;
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
        return enemyType;

    }
}
