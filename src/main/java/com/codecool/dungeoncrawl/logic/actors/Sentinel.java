package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Util;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.ItemType;

import java.util.List;
import java.util.Optional;

public class Sentinel extends Enemy {

    public Sentinel(Cell cell) {
        super(cell, 20);

    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.SKELETON) && !nextCell.getType().equals(CellType.PLAYER) && !nextCell.getType().equals(CellType.CLOSED_DOOR) && !nextCell.getType().equals(CellType.KEY) && !nextCell.getType().equals(CellType.SWORD)) {
            this.getCell().setType(CellType.FLOOR);
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
            this.getCell().setType(CellType.SENTINEL);
        }
    }

    boolean isAlive() {
        return super.getHealth() > 0;
    }

    @Override
    public void attack() {
        Optional<Player> optionalPlayer = Optional.ofNullable(getPLayer());
        if (optionalPlayer.isPresent()) {
            if (optionalPlayer.get().getHealth() > 0) {
                optionalPlayer.get().modifyHealth(5);
            } else
                System.out.println("Player is Dead!");
        }

    }

    @Override
    public String getTileName() {
        return "sentinel";
    }
}
