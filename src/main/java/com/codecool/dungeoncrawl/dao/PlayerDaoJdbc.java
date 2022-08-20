package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.ItemType;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private final DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, inventory, attack) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.setString(5, player.getItemListToString());
            statement.setInt(6, player.getAttack());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    public List<ItemType> stringToList(String stringList) {
        List<ItemType> newList = new ArrayList<>();
        String newString = stringList.substring(0, stringList.lastIndexOf(","));
        String[] newStringList = newString.split(",");
        for (String item : newStringList) {
            switch (item) {
                case "KEY":
                    newList.add(ItemType.KEY);
                    break;
                case "SWORD":
                    newList.add(ItemType.SWORD);
                    break;
                case "HEALTH":
                    newList.add(ItemType.HEALTH);
            }
        }

        return newList;
    }

    @Override
    public PlayerModel getPlayerModel(int id) {
        PlayerModel playerModel = null;
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM player WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            result.next();
            String playerName = result.getString("player_name");
            int x = result.getInt("x");
            int y = result.getInt("y");
            int hp = result.getInt("hp");
            int attack = result.getInt("attack");
            String inventory = result.getString("inventory");
            List<ItemType> itemInventory = stringToList(inventory);

            System.out.println("split inventory  " + inventory);


            System.out.println(playerName + "  player name");
            System.out.println(x + "  player x");
            System.out.println(y + "  player y");
            System.out.println(hp + "  player hp");
            System.out.println(attack + "  player attack");
            System.out.println(itemInventory + "  player itemInventory");


            playerModel = new PlayerModel(playerName,itemInventory,hp, x, y,attack);

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return playerModel;
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
