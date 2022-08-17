package com.codecool.dungeoncrawl.dao;

import annotation.RunNow;
import com.codecool.dungeoncrawl.dao.repositories.EnemyDao;
import com.codecool.dungeoncrawl.dao.repositories.GameStateDao;
import com.codecool.dungeoncrawl.dao.repositories.PlayerDao;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.databasemanager.ApplicationProperties;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    private EnemyDao enemyDao;

    @RunNow
    public void setup() throws SQLException {
        DataSource dataSource = connect();
        gameStateDao = new GameStateDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource);
        enemyDao = new EnemyDaoJdbc(dataSource);
    }

    public PlayerModel getPlayerById(int playerId) {
        return playerDao.get(playerId);
    }

    public void saveGame(String currentMap, Player player, String name) {
        if (getName(name)) {
            PlayerModel model = new PlayerModel(player);
            model.setId(1);
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            GameState gameState = new GameState(currentMap, date, model, name);
            gameStateDao.add(gameState);
        } else {
            dataBaseUpdate();
        }
    }

    public void savePlayer(Player player, String playerName) {
        if (getName(playerName)) {
            player.setName(playerName);
            PlayerModel model = new PlayerModel(player);
            model.setPlayerName(playerName);
            playerDao.add(model);
        } else {
            dataBaseUpdate();
        }
    }

    public void saveEnemy(Enemy enemy, String playerName) {
        if (getName(playerName)) {
            EnemyModel model = new EnemyModel(enemy);
            enemyDao.add(model, playerName);
        } else {
            dataBaseUpdate();
        }
    }

    private void dataBaseUpdate() {
        System.out.println("Update not done yet! :)) Haha Lol!");
    }

    private boolean getName(String name) {
        return gameStateDao.getSaveNameGameState(name) == null;
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
