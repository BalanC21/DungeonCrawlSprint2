package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Archer extends Enemy {

    public Archer(Cell cell) {
        super(cell, 5);
    }

    @Override
    public String getTileName() {
        return "archer";
    }

    @Override
    public void move(int dx, int dy) {

    }

    @Override
    public void attack() {

    }

    @Override
    boolean isAlive() {
        return this.getHealth() > 0;
    }

    @Override
    List<Enemy> getEnemyList() {
        return null;
    }


}
