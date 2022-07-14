package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

import com.codecool.dungeoncrawl.logic.ItemType;

public class Player extends Actor {
    private List<ItemType> itemTypeList;

    public Player(Cell cell) {
        super(cell, 10);
        itemTypeList = new ArrayList<>();
    }

    @Override
    public void attack() {
        List<Enemy> enemyList = getEnemyList();
        if (enemyList.size() != 0) {
            for (Enemy enemy : enemyList) {
                if (enemy.getHealth() >= 5) {
                    enemy.reduceHealth(5);
                }
                if (!enemy.isAlive()) {
                    enemy.getCell().setType(CellType.FLOOR);
                    if (this.getCell().getX() == enemy.getX() && this.getCell().getY() == enemy.getY()) {
                        System.out.println(this.getHealth());
                    }
                }
            }
        }
    }

    @Override
    boolean isAlive() {
        return this.getHealth() != 0;
    }


    @Override
    List<Enemy> getEnemyList() {
        List<Enemy> enemyList = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            if (i == 0) {
                continue;
            }
            if (this.getCell().getNeighbor(i, 0).getType().equals(CellType.SKELETON))
                enemyList.add((Enemy) this.getCell().getNeighbor(i, 0).getActor());
            if (this.getCell().getNeighbor(0, i).getType().equals(CellType.SKELETON))
                enemyList.add((Enemy) this.getCell().getNeighbor(0, i).getActor());
        }
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

    public boolean hasItem(ItemType item) {
        for (ItemType elem : itemTypeList) {
            if (item == elem) {
                return true;
            }
        }
        return false;
    }




}
