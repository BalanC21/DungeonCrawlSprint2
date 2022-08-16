package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Enemy;

public class EnemyModel extends BaseModel{
    private String enemyName;
    private int hp;
    private int x;
    private int y;

    public EnemyModel(String enemyName, int x, int y) {
        this.enemyName = enemyName;
        this.x = x;
        this.y = y;
    }

    public EnemyModel(Enemy enemy) {
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.hp = enemy.getHealth();
    }

    public String getPlayerName() {
        return enemyName;
    }

    public void setPlayerName(String enemyName) {
        this.enemyName = enemyName;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(int anInt) {
    }
}
