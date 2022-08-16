package com.codecool.dungeoncrawl.dao;

import annotation.RunNow;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.manager.ApplicationProperties;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    @RunNow
    public void setup() throws SQLException {
        DataSource dataSource = connect();
        gameStateDao = new GameStateDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource);
    }

    public void savePlayer(Player player, String input) {
        if (getName(player.getName())) {
            player.setName(input);
            PlayerModel model = new PlayerModel(player);
            model.setPlayerName(input);
            playerDao.add(model);
        }
    }

    public PlayerModel getPlayerById(int playerId) {
        return playerDao.get(playerId);
    }

    public void saveGame(String currentMap, Player player, String name) {
        if (getName(player.getName())) {
            PlayerModel model = new PlayerModel(player);
            model.setId(1);
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            GameState gameState = new GameState(currentMap, date, model, name);
            gameStateDao.add(gameState);
        } else {
            update();
        }
    }

    private void update() {
        System.out.println("Update not done yet! :)) Haha Lol!");
    }

    public boolean getName(String name) {
        return gameStateDao.getSaveName(name) == null;
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
