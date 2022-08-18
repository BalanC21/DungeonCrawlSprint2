package com.codecool.dungeoncrawl.dao;

import annotation.RunNow;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.manager.ApplicationProperties;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private EnemiesDao enemiesDao;
    private ItemsDao itemsDao;


    @RunNow
    public void setup() throws SQLException {
        DataSource dataSource = connect();
        gameStateDao = new GameStateDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource);
        enemiesDao = new EnemiesDaoJdbc(dataSource);
        itemsDao = new ItemsDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public PlayerModel getPlayerById(int playerId) {
        return playerDao.get(playerId);
    }


    public boolean entryAlreadySaved(String input){
        return gameStateDao.checkIfSavedInstanceExists(input);

    }


    public void saveGame(String currentMap, Player player, List<Enemy> enemies,  String name, Cell[][] gamemap) {
        PlayerModel playerModel = new PlayerModel(player);
        playerDao.add(playerModel);

        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        GameState gameState = new GameState(currentMap, date, playerModel, name);

        gameStateDao.add(gameState);


        List<EnemyModel> enemyModelList = new ArrayList<>();
        for (Enemy enemy: enemies) {
            enemyModelList.add(new EnemyModel(enemy));
        }
        enemiesDao.add(enemyModelList, gameState.getId());

        ItemModel itemModel = new ItemModel(gamemap);
        List<Cell> itemList = itemModel.getItemList();
        for (Cell cell: itemList) {
            System.out.println(cell.getTileName());
            System.out.println(cell.getX());
            System.out.println( cell.getY());
        }
        System.out.println(gameState.getId() + "   this is the game stat");
        itemsDao.add(itemList, gameState.getId());

    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        ApplicationProperties properties= new ApplicationProperties();

        dataSource.setDatabaseName(properties.readProperty("database"));
        dataSource.setUser(properties.readProperty("user"));
        dataSource.setPassword(properties.readProperty("password"));

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
