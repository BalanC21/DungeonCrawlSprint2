package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Sentinel extends Enemy {
    public Sentinel(Cell cell) {
        super(cell, 20);
    }

    boolean isAlive() {
        return super.getHealth() != 0;
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    void reduceHealth(int value) {

    }

    @Override
    public void attack() {

    }

    @Override
    List<Enemy> getEnemyList() {
        return null;
    }

    @Override
    public String getTileName() {
        return "sentinel";
    }
}
