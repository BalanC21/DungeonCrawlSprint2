package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    @Override
    public void attack() {
        List<Enemy> enemyList = getEnemyList();
        if (enemyList.size() != 0) {
            for (Enemy enemy : enemyList) {
                if (enemy.isAlive())
                    enemy.reduceHealth(5);
            }
        }
    }

    @Override
    boolean isAlive() {
        return this.getHealth() != 0;
    }

    @Override
    void reduceHealth(int value) {
        if (value > this.getHealth())
            this.setAlive(false);
        this.setHealth(this.getHealth() - value);
    }

    @Override
    List<Enemy> getEnemyList() {
        List<Enemy> enemyList = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            if (i == 0) {
                continue;
            }
            if (this.getCell().getNeighbor(i, 0).getType().equals(CellType.SKELETON))
                enemyList.add((Enemy) this.getCell().getNeighbor(i, 0).getActor());
            if (this.getCell().getNeighbor(0, i).getType().equals(CellType.SKELETON))
                enemyList.add((Enemy) this.getCell().getNeighbor(0, i).getActor());
        }
        return enemyList;
    }

    public String getTileName() {
        return "player";
    }
}
