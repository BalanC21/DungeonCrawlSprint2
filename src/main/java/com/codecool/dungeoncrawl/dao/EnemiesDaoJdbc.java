package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnemiesDaoJdbc implements EnemiesDao {
    private final DataSource dataSource;
    List<EnemyModel> enemies;

    public EnemiesDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(List<EnemyModel> enemiesModel, int gameStateId) {
        for (EnemyModel enemy : enemiesModel) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "INSERT INTO enemies (game_state_id, enemy_type, hp, x, y) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, gameStateId);
                statement.setString(2, enemy.getEnemyType());
                statement.setInt(3, enemy.getHp());
                statement.setInt(4, enemy.getX());
                statement.setInt(5, enemy.getY());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                enemy.setId(resultSet.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void update(EnemyModel enemy) {

    }

    @Override
    public EnemyModel get(int gameId) {
        return null;
    }


@Override
    public List<EnemyModel> getEnemieList(int gameId) {
        EnemyModel enemyModel = null;
        enemies = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM enemies WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, gameId);
            ResultSet result = st.executeQuery();
            while (result.next()){
                String enemyType = result.getString("enemy_type");
                int hp = result.getInt("hp");
                int x = result.getInt("x");
                int y = result.getInt("y");

                enemyModel = new EnemyModel(enemyType, x, y, hp);
                enemies.add(enemyModel);
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return enemies;
    }
}
