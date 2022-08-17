package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Archer;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sentinel;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String gameMap, boolean loadFromDb) {
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
                        case 'o' -> cell.setType(CellType.OPEN_DOOR);
                        case 'c' -> cell.setType(CellType.CLOSED_DOOR);

                        default -> System.out.println("First");
                    }
                }
            }
        }
        if (!loadFromDb) {
            try {
                is = MapLoader.class.getResourceAsStream(gameMap);
                assert is != null; // HahaLol :))
                scanner = new Scanner(is);
//                map = new GameMap(width, height, CellType.EMPTY);
                for (int j = 0; j < height; j++) {
                    String line = scanner.nextLine();
                    for (int x = 0; x < width; x++) {
                        if (x < line.length()) {
                            Cell cell = map.getCell(x, j);
                            switch (line.charAt(x)) {

                                case 'i' -> cell.setType(CellType.SWORD);
                                case 'k' -> cell.setType(CellType.KEY);
                                case 'h' -> cell.setType(CellType.HEALTH);

                                case '@' -> {
                                    cell.setType(CellType.PLAYER);
                                    map.setPlayer(new Player(cell));
                                    System.out.println(map.getPlayer().getHealth());
                                }
                                case 's' -> {
                                    cell.setType(CellType.SKELETON);
                                    map.getEnemyList().add(new Skeleton(cell));
                                }
                                case 't' -> {
                                    cell.setType(CellType.SENTINEL);
                                    map.getEnemyList().add(new Sentinel(cell));
                                }
                                case 'a' -> {
                                    cell.setType(CellType.ARCHER);
                                    map.getEnemyList().add(new Archer(cell));
                                }
                                default -> System.out.println("Second");
//                            default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");

                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Ana are mere" + e);
            }
        } else {
            System.out.println("Ana");
//                return map;
        }
        return map;
    }


    public static GameMap loadFromDataBase(GameMap map) {
        return map;
    }
}


//package com.codecool.dungeoncrawl.logic;
//
//import com.codecool.dungeoncrawl.logic.actors.Archer;
//import com.codecool.dungeoncrawl.logic.actors.Player;
//import com.codecool.dungeoncrawl.logic.actors.Sentinel;
//import com.codecool.dungeoncrawl.logic.actors.Skeleton;
//
//import java.io.InputStream;
//import java.util.Scanner;
//
//public class MapLoader {
//    public static GameMap loadMap(String gameMap, boolean loadFromDb) {
//        InputStream is = MapLoader.class.getResourceAsStream(gameMap);
//        Scanner scanner = new Scanner(is);
//        int width = scanner.nextInt();
//        int height = scanner.nextInt();
//
//        scanner.nextLine(); // empty line
//
//        GameMap map = new GameMap(width, height, CellType.EMPTY);
//        for (int y = 0; y < height; y++) {
//            String line = scanner.nextLine();
//            for (int x = 0; x < width; x++) {
//                if (x < line.length()) {
//                    Cell cell = map.getCell(x, y);
//                    switch (line.charAt(x)) {
//                        case ' ' -> cell.setType(CellType.EMPTY);
//                        case '#' -> cell.setType(CellType.WALL);
//                        case '.' -> cell.setType(CellType.FLOOR);
//                        case 'o' -> cell.setType(CellType.OPEN_DOOR);
//                        case 'c' -> cell.setType(CellType.CLOSED_DOOR);
//
//                        case 'i' -> cell.setType(CellType.SWORD);
//                        case 'k' -> cell.setType(CellType.KEY);
//                        case 'h' -> cell.setType(CellType.HEALTH);
//
//                        case '@' -> {
//                            cell.setType(CellType.PLAYER);
//                            map.setPlayer(new Player(cell));
//                        }
//                        case 's' -> {
//                            cell.setType(CellType.SKELETON);
//                            map.getEnemyList().add(new Skeleton(cell));
//                        }
//                        case 't' -> {
//                            cell.setType(CellType.SENTINEL);
//                            map.getEnemyList().add(new Sentinel(cell));
//                        }
//                        case 'a' -> {
//                            cell.setType(CellType.ARCHER);
//                            map.getEnemyList().add(new Archer(cell));
//                        }
//                        default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
//                    }
//                }
//            }
//        }
//        return map;
//    }
//
//    public static GameMap loadFromDataBase(GameMap map) {
//        return map;
//    }
//}
