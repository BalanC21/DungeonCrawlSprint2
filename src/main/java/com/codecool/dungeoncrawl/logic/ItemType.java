package com.codecool.dungeoncrawl.logic;

public enum ItemType {
    SWORD("sword"),
    KEY("key"),
    ARMOUR("armour");

    private final String tileName;

    ItemType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
