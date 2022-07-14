package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    SWORD("sword"),
    KEY("key"),
    CLOSED_DOOR("closed-door"),
    OPEN_DOOR("open-door"),
    SKELETON("skeleton"),
    PLAYER("player"),
    ARCHER("archer"),
    SENTINEL("sentinel"),
    HEALTH("health");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
