package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

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
        return super.getHealth() != 0;
    }

    @Override
    void reduceHealth(int value) {
        if (value > super.getHealth())
            super.setAlive(false);
        int healthRemaining = super.getHealth() - value;
        super.setHealth(healthRemaining);
    }

    @Override
    boolean ifMonster() {
        if (this.getCell().getNeighbor(1, 0).getType().equals(CellType.SKELETON) || this.getCell().getNeighbor(- 1, 0).getType().equals(CellType.SKELETON)) {
            return true;
        }
        return this.getCell().getNeighbor(0, 1).getType().equals(CellType.SKELETON) || this.getCell().getNeighbor(0, -1).getType().equals(CellType.SKELETON);
    }

    public String getTileName() {
        return "player";
    }
}
