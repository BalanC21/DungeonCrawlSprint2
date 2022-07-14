package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Util;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.ItemType;

import java.util.List;

public class Sentinel extends Enemy {

    public Sentinel(Cell cell) {
        super(cell, 20);

    }

    boolean isAlive() {
        return super.getHealth() > 0;
    }

    @Override
    List<Enemy> getEnemyList() {
        return null;
    }


    @Override
    void modifyHealth(int value) {
    }

    @Override
    public void attack() {

    }



    @Override
    public String getTileName() {
        return "sentinel";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            System.out.println("it moves");
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
    }
}
