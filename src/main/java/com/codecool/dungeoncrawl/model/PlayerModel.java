package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.ItemType;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.List;

public class PlayerModel extends BaseModel {

    private String playerName;

    private List<ItemType> itemTypeList;
    private int id;
    private int hp;
    private int x;
    private int y;
    private int attack;

    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(String playerName, List<ItemType> itemTypeList, int hp, int x, int y, int attack) {
        this.playerName = playerName;
        this.itemTypeList = itemTypeList;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.attack = attack;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.hp = player.getHealth();
        this.itemTypeList = player.getItemTypeList();
        this.attack = player.getAttack();

    }

    public int getAttack() {
        return attack;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public List<ItemType> getItemTypeList() {
        return itemTypeList;
    }

    public void setItemTypeList(List<ItemType> itemTypeList) {
        this.itemTypeList = itemTypeList;
    }

    public String getItemListToString() {
        String inventoryToString = "";
        for (ItemType item : getItemTypeList()) {
            inventoryToString = item.toString() + ", ";
        }
        return inventoryToString;
    }
}
