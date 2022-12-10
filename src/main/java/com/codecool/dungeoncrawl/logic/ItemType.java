package com.codecool.dungeoncrawl.logic;

public enum ItemType {
    SWORD("sword"),
    KEY("key"),
    HEALTH("health");
    private final String tileName;
    ItemType(String tileName) {
        this.tileName = tileName;
    }
    public String getTileName() {
        return tileName;
    }
}
