package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;
import java.util.Optional;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell, 10);
    }

    @Override
    public void move(int dx, int dy) {

    }

    public void attack() {
        Optional<Player> optionalPlayer = Optional.ofNullable(getPLayer());
        if (optionalPlayer.isPresent()) {
            if (optionalPlayer.get().getHealth() != 0) {
                System.out.println("Enemy Attack");
                optionalPlayer.get().reduceHealth(2);
            } else
                System.out.println("Player is Dead!");
        }
    }

    @Override
    boolean isAlive() {
        return super.getHealth() != 0;
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
