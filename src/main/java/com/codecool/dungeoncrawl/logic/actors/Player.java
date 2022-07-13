package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

import com.codecool.dungeoncrawl.logic.ItemType;

public class Player extends Actor {
    private List<ItemType> itemTypeList;

    public Player(Cell cell) {
        super(cell);
        itemTypeList = new ArrayList<>();
    }

    @Override
    public void attack() {
        List<Enemy> enemyList = getEnemyList();
        if (enemyList.size() != 0) {
            for (Enemy enemy : enemyList) {
                if (enemy.getHealth() != 0)
                    enemy.reduceHealth(5);
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
                    if (this.getCell().getX() == enemy.getX() && this.getCell().getY() == enemy.getY() + i) {
                        this.reduceHealth(-2);
                    }
                    if (this.getCell().getX() == enemy.getX() - i && this.getCell().getY() == enemy.getY()) {
                        this.reduceHealth(-2);
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
    void reduceHealth(int value) {
        if (value > this.getHealth())
            this.setAlive(false);
        this.setHealth(this.getHealth() - value);
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
