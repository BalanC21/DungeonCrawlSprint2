package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.ItemType;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private List<ItemType> itemTypeList;
    public Player(Cell cell) {
        super(cell);
        itemTypeList = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }

    public void addItem(ItemType itemType){
        itemTypeList.add(itemType);
    }

    public List<ItemType> getItemTypeList() {
        return itemTypeList;
    }
}
