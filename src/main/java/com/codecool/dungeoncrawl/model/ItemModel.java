package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class ItemModel extends BaseModel {
    Cell[][] gameMap;
    private String type;
    private int gameStateId;
    private int x;
    private int y;

    public ItemModel(Cell[][] gameMap) {
        this.gameMap = gameMap;
    }

    public ItemModel(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }


    public List<Cell> getItemList() {
        List<Cell> itemList = new ArrayList();
        for (Cell[] row : gameMap) {
            for (Cell cell : row) {
                if (cell.getType()== CellType.KEY || cell.getType()== CellType.HEALTH || cell.getType()== CellType.SWORD){
                    itemList.add(cell);
                }
            }
        }
        return itemList;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}
