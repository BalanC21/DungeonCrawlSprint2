package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public abstract class Enemy extends Actor{

    public Enemy(Cell cell) {
        super(cell);
    }

    @Override
    abstract void attack();
}
