package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.ItemType;

import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;
    private boolean isCharacterAlive;
    private int health;

    public Actor(Cell cell, int health) {
        this.cell = cell;
        this.isCharacterAlive = true;
        this.cell.setActor(this);
        this.health = health;
    }

    void reduceHealth(int value) {
        if (value > this.getHealth())
            this.setAlive(false);
        this.setHealth(this.getHealth() - value);
    }

    public void setAlive(boolean alive) {
        isCharacterAlive = alive;
    }

    public boolean isCharacterAlive() {
        return isCharacterAlive;
    }

    public void setCharacterAlive(boolean characterAlive) {
        isCharacterAlive = characterAlive;
    }

    public abstract void move(int dx, int dy);

    public abstract void attack();

    abstract boolean isAlive();

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
