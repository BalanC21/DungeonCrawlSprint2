package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Util;
import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Sentinel extends Enemy {

    public Sentinel(Cell cell) {
        super(cell, 20);

    }

    boolean isAlive() {
        return super.getHealth() != 0;
    }

    @Override
    void reduceHealth(int value) {
    }

    @Override
    public void attack() {

    }



    @Override
    public String getTileName() {
        return "sentinel";
    }

    @Override
    public void move(int dx, int dy) {
        getCell().getActor().move(Util.getRandomInt(), Util.getRandomInt());
    }
}
