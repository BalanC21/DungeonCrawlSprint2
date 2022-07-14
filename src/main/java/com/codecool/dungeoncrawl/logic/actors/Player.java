package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.codecool.dungeoncrawl.logic.ItemType;

public class Player extends Actor {
    private List<ItemType> itemTypeList;

    private CellType cellType;

    public Player(Cell cell) {
        super(cell, 10);
        this.cellType = CellType.PLAYER;
        itemTypeList = new ArrayList<>();
    }

    public CellType getCellType() {
        return cellType;
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.SKELETON) && !nextCell.getType().equals(CellType.CLOSED_DOOR)) {
            if(this.getCell().getType().equals(CellType.PLAYER)){
                    this.getCell().setType(CellType.FLOOR);
                }
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
        if (((Player) this.getCell().getActor()).hasItem(ItemType.KEY)) {
            if (!nextCell.getType().equals(CellType.WALL) && !(this.getCell().getActor().getX() == 17 & this.getCell().getActor().getY() == 3) && !nextCell.getType().equals(CellType.SKELETON)) {
                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
            }
        }
    }

    @Override
    public void attack() {
        Optional<Enemy> enemyOptional;

        System.out.println(this.getCell().getType());
        List<Enemy> enemyList = getEnemyList();
        if (enemyList.size() != 0) {
            for (Enemy enemy : enemyList) {
                enemyOptional = Optional.ofNullable(enemy);
                if (enemyOptional.isPresent()){
                    if (enemyOptional.get().getHealth() != 0)
                        enemyOptional.get().reduceHealth(5);}
            }
        }
    }

    public void lootEnemy() {
        List<Enemy> enemyList = getEnemyList();
        Optional<Enemy> enemyOptional;
        // TODO: 13.07.2022 Try to repair this!
        for (Enemy enemy : enemyList) {
            enemyOptional = Optional.ofNullable(enemy);
            if (enemyOptional.isPresent()) {
                if (!enemyOptional.get().isAlive()) {
                    enemyOptional.get().getCell().setType(CellType.FLOOR);
                    for (int i = -1; i < 2; i++) {
                        if (i == 0)
                            continue;
                        if (this.getCell().getX() == enemyOptional.get().getX() && this.getCell().getY() == enemyOptional.get().getY() + i)
                            this.reduceHealth(-2);
                        if (this.getCell().getX() == enemyOptional.get().getX() - i && this.getCell().getY() == enemyOptional.get().getY())
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
    public List<Enemy> getEnemyList() {
        List<Enemy> enemyList = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            if (i == 0)
                continue;
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
