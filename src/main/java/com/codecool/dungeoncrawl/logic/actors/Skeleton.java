package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.List;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell,10);
    }

    @Override
    void attack() {

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
