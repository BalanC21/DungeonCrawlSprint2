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
        boolean ana = ifMonster();
        System.out.println(ana);
        if (ana)
            System.out.println("Entering Boolean");
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
    boolean ifMonster() {
        for (int i = -1; i < 2; i++) {
            if (i == 0) {
                continue;
            }
            if (this.getCell().getNeighbor(i, 0).getType().equals(CellType.SKELETON))
                return true;
            if (this.getCell().getNeighbor(0, i).getType().equals(CellType.SKELETON))
                return true;
        }
        return false;
    }

    public String getTileName() {
        return "player";
    }
}
