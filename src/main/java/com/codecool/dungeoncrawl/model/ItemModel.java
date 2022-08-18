package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;

public class ItemModel extends BaseModel {
    Cell[][] gameMap;
    private CellType type;
    private int x;
    private int y;

    public ItemModel(Cell[][] gameMap) {
        this.gameMap = gameMap;
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


}
