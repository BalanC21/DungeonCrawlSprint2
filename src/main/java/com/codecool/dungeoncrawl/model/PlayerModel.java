package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.ItemType;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.List;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int attack;
    private int x;
    private int y;
    private List<ItemType> itemTypeList;

    public PlayerModel(String playerName, int hp, int attack, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.attack = attack;
    }

    public PlayerModel(String playerName, int hp, int attack, int x, int y, int id) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.attack = attack;
        this.id = id;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.hp = player.getHealth();
        this.attack = player.getAttack();
        this.itemTypeList = player.getAllItems();
        System.out.println(itemTypeList);
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
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
    public String toString() {
        return "PlayerModel{" +
                "playerName='" + playerName + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", itemTypeList=" + itemTypeList +
                '}';
    }
}

