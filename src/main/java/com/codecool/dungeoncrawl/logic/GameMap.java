package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private Cell[][] cells;
    private Player player;
    private List<Enemy> enemyList;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.enemyList = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
    public Cell setCell(int x, int y) {
        return cells[x][y];
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
