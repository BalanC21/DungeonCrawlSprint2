package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Archer;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sentinel;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String gameMap) {
        InputStream is = MapLoader.class.getResourceAsStream(gameMap);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ' -> cell.setType(CellType.EMPTY);
                        case '#' -> cell.setType(CellType.WALL);
                        case '.' -> cell.setType(CellType.FLOOR);
                        case 's' -> {
                            cell.setType(CellType.SKELETON);
                            map.getEnemyList().add(new Skeleton(cell));
                        }
                        case '@' -> {
                            cell.setType(CellType.PLAYER);
                            map.setPlayer(new Player(cell));
                        }
                        case 'i' -> cell.setType(CellType.SWORD);
                        case 'k' -> cell.setType(CellType.KEY);
                        case 'o' -> cell.setType(CellType.OPEN_DOOR);
                        case 'h' -> cell.setType(CellType.HEALTH);
                        case 'c' -> cell.setType(CellType.CLOSED_DOOR);
                        case 't' -> {
                            cell.setType(CellType.SENTINEL);
                            map.getEnemyList().add(new Sentinel(cell));
                        }
                        case 'a' -> {
                            cell.setType(CellType.ARCHER);
                            map.getEnemyList().add(new Archer(cell));
                        }
                        default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
