package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    @Override
    public void attack() {
//        if (super.getCell().getActor().equals(CellType.))
    }

    public String getTileName() {
        return "player";
    }
}
