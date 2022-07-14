package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

import com.codecool.dungeoncrawl.logic.ItemType;

public class Player extends Actor {
    private List<ItemType> itemTypeList;
    private List<Enemy> enemyList;

    private CellType cellType;

    public Player(Cell cell) {
        super(cell, 10);
        this.cellType = CellType.PLAYER;
        itemTypeList = new ArrayList<>();
        enemyList = new ArrayList<>();
    }

    public CellType getCellType() {
        return cellType;
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.SKELETON) && !nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
        if (hasItem(ItemType.KEY)) {
            if (!nextCell.getType().equals(CellType.WALL) && !(this.getCell().getActor().getX() == 17 & this.getCell().getActor().getY() == 3) && !nextCell.getType().equals(CellType.SKELETON)) {
                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
            }
        }
    }

    @Override
    public void attack() {
        List<Enemy> enemyList = getEnemyList();
        if (enemyList.size() != 0) {
            for (Enemy enemy : enemyList) {
                if (enemy.getHealth() != 0) {
                    System.out.println("Player attack");
                    enemy.reduceHealth(5);
                    System.out.println(enemy.getHealth());
                }
            }
        }
    }

    public void lootEnemy() {
        List<Enemy> enemyList = getEnemyList();
        // TODO: 13.07.2022 Try to repair this!
        for (Enemy enemy : enemyList) {
            if (!enemy.isAlive()) {
                enemy.getCell().setType(CellType.FLOOR);
                for (int i = -1; i < 2; i++) {
                    if (i == 0)
                        continue;
                    if (this.getCell().getX() == enemy.getX() && this.getCell().getY() == enemy.getY() + i)
                        this.reduceHealth(-2);
                    if (this.getCell().getX() == enemy.getX() - i && this.getCell().getY() == enemy.getY())
                        this.reduceHealth(-2);
                }
            }
        }
    }
    @Override
    boolean isAlive() {
        return this.getHealth() != 0;
    }


    private void addEnemyToList() {
        for (int i = -1; i < 2; i++) {
            if (i == 0)
                continue;
            if (this.getCell().getNeighbor(i, 0).getType().equals(CellType.SKELETON))
                enemyList.add((Enemy) this.getCell().getNeighbor(i, 0).getActor());
            if (this.getCell().getNeighbor(0, i).getType().equals(CellType.SKELETON))
                enemyList.add((Enemy) this.getCell().getNeighbor(0, i).getActor());
        }
    }


    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public String getTileName() {
        return "player";
    }


    public void addItem(ItemType itemType) {
        itemTypeList.add(itemType);
    }

    public List<ItemType> getItemTypeList() {
        return itemTypeList;
    }

    private boolean hasItem(ItemType item) {
        for (ItemType elem : itemTypeList) {
            if (item == elem) {
                return true;
            }
        }
        return false;
    }


}
