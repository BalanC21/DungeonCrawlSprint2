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
        System.out.println(this.getHealth());
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

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.SKELETON) && !nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        if (((Player) cell.getActor()).hasItem(ItemType.KEY)) {
            if (!nextCell.getType().equals(CellType.WALL) && !(cell.getActor().getX() == 17 & cell.getActor().getY() == 3) && !nextCell.getType().equals(CellType.SKELETON)) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public abstract void attack();

    abstract boolean isAlive();

    abstract List<Enemy> getEnemyList();

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
