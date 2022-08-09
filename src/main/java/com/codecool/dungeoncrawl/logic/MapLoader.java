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
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.SKELETON);
                            map.getEnemyList().add(new Skeleton(cell));
                            break;
                        case '@':
                            cell.setType(CellType.PLAYER);
                            PlayerModel playerModel = new PlayerModel("Player", x, y);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'i':
                            cell.setType(CellType.SWORD);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            break;
                        case 'o':
                            cell.setType(CellType.OPEN_DOOR);
                            break;
                        case 'h':
                            cell.setType(CellType.HEALTH);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 't':
                            cell.setType(CellType.SENTINEL);
                            map.getEnemyList().add(new Sentinel(cell));
                            break;
                        case 'a':
                            cell.setType(CellType.ARCHER);
                            map.getEnemyList().add(new Archer(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
