package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Enemy;

public class EnemyModel extends BaseModel{
    private String enemyName;
    private int hp;
    private int x;
    private int y;

    public EnemyModel(Enemy enemy) {
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.hp = enemy.getHealth();
        this.enemyName = enemy.getTileName();
        System.out.println(enemyName);
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(int anInt) {
        this.id = anInt;
    }
}
