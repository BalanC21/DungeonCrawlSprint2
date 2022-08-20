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
import java.sql.SQLException;
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


    public boolean checkGameExists(String input) {
        return gameStateDao.checkIfSavedInstanceExists(input);
    }

    public void saveGame(String currentMap, Player player, List<Enemy> enemies, String name, Cell[][] gamemap) {
        PlayerModel playerModel = new PlayerModel(player);
        playerDao.add(playerModel);

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        GameState gameState = new GameState(currentMap, date, name);

        gameStateDao.add(gameState, playerModel.getId());


        List<EnemyModel> enemyModelList = new ArrayList<>();
        for (Enemy enemy : enemies) {
            String enemyType = enemy.getCellType();
            int hp = enemy.getHealth();
            int x = enemy.getX();
            int y = enemy.getY();

            enemyModelList.add(new EnemyModel(enemyType, x, y, hp));
        }
        enemiesDao.add(enemyModelList, gameState.getId());
        ItemModel itemModel = new ItemModel(gamemap);
        List<Cell> itemList = itemModel.getItemList();
        itemsDao.add(itemList, gameState.getId());
    }

    public int getPlayerId(String name){
        return gameStateDao.getPlayerId(name);
    }

    public PlayerModel getPlayerModel(int id){
        return playerDao.getPlayerModel(id);
    }

    public GameState getGameModel(String name) {
        return gameStateDao.get(name);
    }

    public List<ItemModel> getItemModels(int gameId) {
        return itemsDao.getItemModelList(gameId);
    }

    public List<EnemyModel> getEnemiesDao(int gameId) {
        return enemiesDao.getEnemieList(gameId);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        ApplicationProperties properties = new ApplicationProperties();

        dataSource.setDatabaseName(properties.readProperty("database"));
        dataSource.setUser(properties.readProperty("user"));
        dataSource.setPassword(properties.readProperty("password"));

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
