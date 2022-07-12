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
        List<Enemy> enemyList = ifMonster();
        if (ifMonster().size() != 0) {
            System.out.println(enemyList.get(0).getTileName());
            System.out.println(ifMonster().size());
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
        int healthRemaining = this.getHealth() - value;
        this.setHealth(healthRemaining);
    }

    @Override
    List<Enemy> ifMonster() {
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
