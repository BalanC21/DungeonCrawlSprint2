package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private boolean isCharacterAlive;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.isCharacterAlive = true;
        this.cell.setActor(this);
    }
    public void setAlive(boolean alive) {
        isCharacterAlive = alive;
    }
    public boolean isCharacterAlive() {
        return isCharacterAlive;
    }
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.SKELETON)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }
    abstract void attack();
    abstract boolean isAlive();
    abstract void reduceHealth(int value);

    abstract boolean ifMonster();

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
