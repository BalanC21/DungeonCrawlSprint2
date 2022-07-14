package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell);
    }

    public void attack() {
        Player player = getPLayer();
        if (player != null) {
            if (player.getHealth() != 0)
                player.reduceHealth(2);
            else
                System.out.println("Player is Dead!");
        }
    }

    @Override
    boolean isAlive() {
        return super.getHealth() != 0;
    }

    @Override
    void reduceHealth(int value) {
        if (this.isCharacterAlive())
            this.setHealth(this.getHealth() - value);
        else
            this.setCharacterAlive(false);
    }

    @Override
    List<Enemy> getEnemyList() {
        return null;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
