package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    void attack() {

    }

    @Override
    boolean isAlive() {
        return super.getHealth() != 0;
    }

    @Override
    void reduceHealth(int value) {

    }

    @Override
    List<Enemy> ifMonster() {
        return null;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
