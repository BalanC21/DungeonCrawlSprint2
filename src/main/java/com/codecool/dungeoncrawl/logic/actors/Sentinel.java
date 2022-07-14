package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Sentinel extends Enemy{



    public Sentinel(Cell cell) {
        super(cell, 20);
    }

    @Override
    void attack() {

    }

    boolean isAlive() {
        return super.getHealth() != 0;
    }

    @Override
    void reduceHealth(int value) {

    }

    @Override
    List<Enemy> getEnemyList() {
        return null;
    }

    @Override
    public String getTileName() {
        return null;
    }
}
