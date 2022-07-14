package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Archer extends Enemy{

    public Archer(Cell cell) {
        super(cell, 5);
    }

    @Override
    public String getTileName() {
        return "archer";
    }

    @Override
    boolean isAlive() {
//        if ()
        return false;
    }

    @Override
    List<Enemy> getEnemyList() {
        return null;
    }


    @Override
    void attack() {

    }
}
