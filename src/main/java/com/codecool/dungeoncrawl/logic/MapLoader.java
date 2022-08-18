package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Archer;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Sentinel;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.InventoryRecord;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MapLoader {

    private final GameDatabaseManager gameDatabaseManager;
    private PlayerModel playerModel;

    public MapLoader(GameDatabaseManager gameDatabaseManager) {
        this.gameDatabaseManager = gameDatabaseManager;
    }

    public GameMap loadMap(String gameMap, boolean loadFromDb) throws SQLException {
        if (gameDatabaseManager != null)
            gameDatabaseManager.setup("Lulu");
        InputStream is = MapLoader.class.getResourceAsStream(gameMap);
        
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
//        GameMap map = new GameMap(width, height, CellType.FLOOR);
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
                        default -> System.out.print("");
                    }
                }
            }
        }
        if (!loadFromDb) {
            try {
                is = MapLoader.class.getResourceAsStream(gameMap);
                assert is != null; // HahaLol :))
                scanner = new Scanner(is);
                scanner.nextLine();
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
                                default -> System.out.print("");
//                            default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Ana are mere" + e);
            }
        } else {
            playerModel = gameDatabaseManager.getPlayerModel("cancel");
            List<EnemyModel> enemyModels = gameDatabaseManager.getAllEnemies("cancel");
            loadPlayerFromDataBase(map, playerModel);
            loadEnemiesFromDataBase(map, enemyModels);
        }
        return map;
    }


    private void loadPlayerFromDataBase(GameMap map, PlayerModel playerModel) {
        Cell cell = map.getCell(playerModel.getX(), playerModel.getY());
        Player player = new Player(cell);
        player.setHealth(playerModel.getHp());
        player.setAttack(playerModel.getAttack());
        map.setPlayer(player);
    }

    private void loadEnemiesFromDataBase(GameMap map, List<EnemyModel> enemyModels) {
        for (EnemyModel enemyModel : enemyModels) {
            Cell cell = map.getCell(enemyModel.getX(), enemyModel.getY());
            if (enemyModel.getEnemyName().equals("sentinel")) {
                cell.setType(CellType.SENTINEL);
                map.getEnemyList().add(new Sentinel(cell));
            }
            if (enemyModel.getEnemyName().equals("archer")) {
                cell.setType(CellType.ARCHER);
                map.getEnemyList().add(new Archer(cell));
            }
            if (enemyModel.getEnemyName().equals("skeleton")) {
                cell.setType(CellType.SKELETON);
                map.getEnemyList().add(new Skeleton(cell));
            }
        }
    }

    private void loadItemsFromDataBase(GameMap map, List<InventoryRecord> inventoryRecords) {
        for (InventoryRecord inventoryRecord : inventoryRecords) {
            if (inventoryRecord.itemName().equals("sword"));
        }
    }
}