package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.Optional;

public abstract class Enemy extends Actor {


    public Enemy(Cell cell, int health) {
        super(cell, health);
    }


    public Player getPLayer() {
        Player player = null;
        for (int i = -1; i < 2; i++) {
            if (i == 0)
                continue;
            if (this.getCell().getNeighbor(i, 0).getGameMap().getPlayer().getCellType().equals(CellType.PLAYER))
                player = ((Player) this.getCell().getNeighbor(i, 0).getActor());
            if (this.getCell().getNeighbor(0, i).getGameMap().getPlayer().getCellType().equals(CellType.PLAYER))
                player = ((Player) this.getCell().getNeighbor(0, i).getActor());
        }
        return player;
    }
}
